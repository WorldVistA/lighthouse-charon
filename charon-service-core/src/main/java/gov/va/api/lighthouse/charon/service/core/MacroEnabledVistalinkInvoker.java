package gov.va.api.lighthouse.charon.service.core;

import static gov.va.api.lighthouse.charon.service.core.CharonVistaLinkManagedConnection.socketTimeout;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.IsPrincipal;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.service.core.macro.MacroExecutionContext;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessor;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessorFactory;
import gov.va.med.vistalink.rpc.NoRpcContextFaultException;
import gov.va.med.vistalink.rpc.RpcNotInContextFaultException;
import gov.va.med.vistalink.rpc.RpcNotOkForProxyUseException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.function.BiFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(onlyExplicitlyIncluded = true)
public class MacroEnabledVistalinkInvoker implements VistalinkInvoker, MacroExecutionContext {

  @Getter private final ConnectionDetails connectionDetails;

  private final VistalinkSession session;

  private final MacroProcessorFactory macroProcessorFactory;

  @Builder
  MacroEnabledVistalinkInvoker(
      IsPrincipal rpcPrincipal,
      ConnectionDetails connectionDetails,
      MacroProcessorFactory macroProcessorFactory,
      BiFunction<IsPrincipal, ConnectionDetails, VistalinkSession> optionalSessionSelection) {
    this.connectionDetails = connectionDetails;
    this.macroProcessorFactory = macroProcessorFactory;
    /*
     * This is extensible to allow testing of the rest of this class. Under normal circumstances,
     * you do not need to specify this.
     */
    if (optionalSessionSelection == null) {
      optionalSessionSelection = VistalinkSessionFactory::create;
    }
    this.session = optionalSessionSelection.apply(rpcPrincipal, connectionDetails);
  }

  @Override
  @SneakyThrows
  public VistalinkXmlResponse buildRequestAndInvoke(RpcDetails rpcDetails) {
    var start = Instant.now();
    try {
      var vistalinkRequest = RpcRequestFactory.getRpcRequest();
      vistalinkRequest.setRpcContext(rpcDetails.context());
      vistalinkRequest.setUseProprietaryMessageFormat(true);
      vistalinkRequest.setRpcName(rpcDetails.name());
      if (rpcDetails.version().isPresent()) {
        vistalinkRequest.setRpcVersion(rpcDetails.version().get());
      }
      MacroProcessor macroProcessor = macroProcessorFactory.create(this);
      for (int i = 0; i < rpcDetails.parameters().size(); i++) {
        var parameter = rpcDetails.parameters().get(i);
        var value = macroProcessor.evaluate(parameter);
        vistalinkRequest.getParams().setParam(i + 1, parameter.type(), value);
      }
      vistalinkRequest.setTimeOut(socketTimeout() + 4);
      RpcResponse vistalinkResponse = invoke(vistalinkRequest);
      log.info("{} Response {} chars", this, vistalinkResponse.getRawResponse().length());
      return VistalinkXmlResponse.parse(vistalinkResponse);
    } catch (NoRpcContextFaultException
        | RpcNotInContextFaultException
        | RpcNotOkForProxyUseException e) {
      throw new UnrecoverableVistalinkExceptions.BadRpcContext(rpcDetails.context(), e);
    } finally {
      log.info(
          "{} {} ms for {}",
          this,
          Duration.between(start, Instant.now()).toMillis(),
          rpcDetails.name());
    }
  }

  @Override
  public void close() {
    session.close();
  }

  /** Invoke an RPC with raw types. */
  @Override
  @SneakyThrows
  public RpcResponse invoke(RpcRequest request) {
    log.info(
        "Connecting to {} as type {}",
        connectionDetails.name(),
        session.getClass().getSimpleName());
    log.info("{} Executing RPC {}", this, request.getRpcName());
    return session.connection().executeRPC(request);
  }

  @ToString.Include
  @Override
  public String vista() {
    return connectionDetails.name();
  }
}
