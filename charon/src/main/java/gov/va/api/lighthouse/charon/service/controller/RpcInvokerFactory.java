package gov.va.api.lighthouse.charon.service.controller;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.RpcPrincipal;

/** Interface for defining RpcInvokerFactory's. */
public interface RpcInvokerFactory {
  RpcInvoker create(RpcPrincipal rpcPrincipal, ConnectionDetails details);
}
