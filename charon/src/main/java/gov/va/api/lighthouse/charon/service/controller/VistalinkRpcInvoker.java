package gov.va.api.lighthouse.charon.service.controller;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.IsPrincipal;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.api.RpcMetadata;
import gov.va.api.lighthouse.charon.service.core.MacroEnabledVistalinkInvoker;
import gov.va.api.lighthouse.charon.service.core.VistalinkInvoker;
import gov.va.api.lighthouse.charon.service.core.VistalinkSession;
import gov.va.api.lighthouse.charon.service.core.VistalinkXmlResponse;
import gov.va.api.lighthouse.charon.service.core.macro.MacroExecutionContext;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessorFactory;
import java.util.function.BiFunction;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Delegate;

/** Invoker class that makes requests to a vista site's rpcs. */
@ToString(onlyExplicitlyIncluded = true)
public class VistalinkRpcInvoker implements RpcInvoker, MacroExecutionContext {

  @Delegate VistalinkInvoker invoker;

  @Builder
  VistalinkRpcInvoker(
      IsPrincipal rpcPrincipal,
      ConnectionDetails connectionDetails,
      MacroProcessorFactory macroProcessorFactory,
      BiFunction<IsPrincipal, ConnectionDetails, VistalinkSession> optionalSessionSelection) {
    this.invoker =
        MacroEnabledVistalinkInvoker.builder()
            .rpcPrincipal(rpcPrincipal)
            .connectionDetails(connectionDetails)
            .macroProcessorFactory(macroProcessorFactory)
            .optionalSessionSelection(optionalSessionSelection)
            .build();
  }

  @Override
  @SneakyThrows
  public RpcInvocationResult invoke(RpcDetails rpcDetails) {
    VistalinkXmlResponse xmlResponse = invoker.buildRequestAndInvoke(rpcDetails);
    return RpcInvocationResult.builder()
        .vista(vista())
        .metadata(RpcMetadata.builder().timezone(connectionDetails().timezone()).build())
        .response(xmlResponse.getResponse().getValue())
        .build();
  }
}
