package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;

public interface RpcInvokerV1 extends AutoCloseable {
  // redefined to remove exception from the signature
  @Override
  void close();

  RpcInvocationResultV1 invoke();
}
