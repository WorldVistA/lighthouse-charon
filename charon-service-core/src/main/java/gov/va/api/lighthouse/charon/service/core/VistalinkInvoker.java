package gov.va.api.lighthouse.charon.service.core;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

public interface VistalinkInvoker extends AutoCloseable {

  VistalinkXmlResponse buildRequestAndInvoke(RpcDetails request);

  // redefined to remove exception from the signature
  @Override
  void close();

  ConnectionDetails connectionDetails();

  RpcResponse invoke(RpcRequest request);

  String vista();
}
