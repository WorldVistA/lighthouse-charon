package gov.va.api.lighthouse.charon.service.core;

import java.io.Serial;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class UnrecoverableVistalinkExceptions {

  public static class BadRpcContext extends UnrecoverableVistalinkException {

    @Serial private static final long serialVersionUID = 6167518157729577717L;

    public BadRpcContext(String rpcContext, Throwable cause) {
      super(rpcContext, cause);
    }
  }

  public static class LoginFailure extends UnrecoverableVistalinkException {

    @Serial private static final long serialVersionUID = 4860029675281471237L;

    public LoginFailure(String message) {
      super(message, null);
    }
  }

  public static class UnrecoverableVistalinkException extends RuntimeException {

    @Serial private static final long serialVersionUID = -6893741398933405923L;

    public UnrecoverableVistalinkException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
