package gov.va.api.lighthouse.charon.service.core;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class RetryingVistalinkInvoker implements VistalinkInvoker {
  private final int maxRetries;

  VistalinkInvoker delegate;

  List<Predicate<Throwable>> retryChecks;

  /** Wrap another invoker with retry capability with a default configuration. */
  public static RetryingVistalinkInvoker wrap(VistalinkInvoker wrapMe) {
    return RetryingVistalinkInvoker.builder()
        .delegate(wrapMe)
        .maxRetries(3)
        .retryChecks(RetryChecks.defaultRetryChecks())
        .build();
  }

  public VistalinkXmlResponse buildRequestAndInvoke(RpcDetails request) {
    return retry(() -> delegate.buildRequestAndInvoke(request));
  }

  @Override
  public void close() {
    delegate.close();
  }

  @Override
  public ConnectionDetails connectionDetails() {
    return delegate.connectionDetails();
  }

  String exceptionHierarchy(Exception topmostException) {
    StringBuilder chain = new StringBuilder();
    /*
     * We don't really care about the root cause, but we will use this to walk the exception cause
     * hierarchy.
     */
    RetryChecks.hasRootCause(
        topmostException,
        cause -> {
          if (chain.length() > 0) {
            chain.append(" > ");
          }
          chain.append(cause.getClass().getSimpleName());
          return false;
        });
    return chain.toString();
  }

  @Override
  public RpcResponse invoke(RpcRequest request) {
    return retry(() -> delegate.invoke(request));
  }

  private <T> T retry(Supplier<T> action) {
    int attempt = 0;
    while (true) {
      attempt++;
      try {
        return action.get();
      } catch (Exception e) {
        if (attempt >= maxRetries || retryChecks.stream().noneMatch(check -> check.test(e))) {
          log.warn("Not retrying attempt {} on {}", attempt, exceptionHierarchy(e));
          throw e;
        }
        log.warn("Retry {} on {}", attempt, exceptionHierarchy(e));
      }
    }
  }

  @Override
  public String vista() {
    return delegate.vista();
  }

  private static class RetryChecks {
    private static List<Predicate<Throwable>> defaultRetryChecks() {
      return List.of(
          hasRootCause(isType(SocketTimeoutException.class)),
          hasRootCause(isType(ConnectException.class)));
    }

    static boolean hasRootCause(Throwable e, Predicate<Throwable> causeCheck) {
      Throwable cause = e;
      while (cause != null) {
        if (causeCheck.test(cause)) {
          return true;
        }
        cause = cause.getCause();
      }
      return false;
    }

    static Predicate<Throwable> hasRootCause(Predicate<Throwable> causeCheck) {
      return throwable -> hasRootCause(throwable, causeCheck);
    }

    static Predicate<Throwable> isType(Class<? extends Throwable> type) {
      return type::isInstance;
    }
  }
}
