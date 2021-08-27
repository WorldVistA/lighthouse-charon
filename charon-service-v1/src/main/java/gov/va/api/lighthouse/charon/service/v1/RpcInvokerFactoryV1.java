package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;

public interface RpcInvokerFactoryV1 {
  RpcInvokerV1 create(RpcRequestV1 request, ConnectionDetails connectionDetails);
}
