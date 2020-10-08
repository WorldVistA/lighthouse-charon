package gov.va.api.lighthouse.vistalink.service.controller;

import gov.va.api.lighthouse.vistalink.service.api.RpcPrincipal;
import gov.va.api.lighthouse.vistalink.service.config.VistalinkProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class VistalinkRpcInvokerFactory implements RpcInvokerFactory {

  VistalinkProperties vistalinkProperties;

  // TODO
  @Override
  public RpcInvoker create(RpcPrincipal rpcPrincipal, String name) {
    return null;
  }
}
