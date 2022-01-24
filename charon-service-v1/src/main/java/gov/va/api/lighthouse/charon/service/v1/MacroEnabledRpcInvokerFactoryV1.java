package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.core.MacroEnabledVistalinkInvoker;
import gov.va.api.lighthouse.charon.service.core.RetryingVistalinkInvoker;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MacroEnabledRpcInvokerFactoryV1 implements RpcInvokerFactoryV1 {

  private final MacroProcessorFactory macroProcessorFactory;

  @Override
  public RpcInvokerV1 create(RpcRequestV1 request, ConnectionDetails connectionDetails) {
    return DelegatingRpcInvokerV1.builder()
        .details(request.rpc())
        .invoker(
            RetryingVistalinkInvoker.wrap(
                MacroEnabledVistalinkInvoker.builder()
                    .rpcPrincipal(request.principal())
                    .macroProcessorFactory(macroProcessorFactory)
                    .connectionDetails(connectionDetails)
                    .build()))
        .build();
  }
}
