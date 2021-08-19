package gov.va.api.lighthouse.charon.service.controller;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.RpcVistaTargets;
import java.util.List;

/** Interface for defining how to resolve connections to a given vista site. */
public interface VistaNameResolver {
  List<ConnectionDetails> resolve(RpcVistaTargets rpcVistaTargets);
}
