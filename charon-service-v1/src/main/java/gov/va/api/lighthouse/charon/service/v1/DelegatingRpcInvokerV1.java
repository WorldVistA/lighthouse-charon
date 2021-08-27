package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.service.core.VistalinkInvoker;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter(AccessLevel.PACKAGE) // unit test support
public class DelegatingRpcInvokerV1 implements RpcInvokerV1 {

  private final RpcDetails details;
  private final VistalinkInvoker invoker;

  @Override
  public RpcInvocationResultV1 invoke() {
    var response = invoker.buildRequestAndInvoke(details);
    return RpcInvocationResultV1.builder()
        .vista(invoker.vista())
        .timezone(invoker.connectionDetails().timezone())
        .response(response.getResponse().getValue())
        .build();
  }
}
