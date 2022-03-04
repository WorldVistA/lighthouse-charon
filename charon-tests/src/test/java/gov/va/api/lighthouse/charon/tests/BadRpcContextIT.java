package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeVistaIsAvailable;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.v1.ErrorResponseV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class BadRpcContextIT {
  @Test
  @SneakyThrows
  void requestForbiddenRpcContext() {
    assumeVistaIsAvailable();

    RpcRequestV1 body =
        RpcRequestV1.builder()
            .rpc(
                RpcDetails.builder()
                    .context("NOPE CONTEXT")
                    .name("VPR GET PATIENT DATA JSON")
                    .build())
            .principal(SystemDefinitions.get().avCodePrincipal())
            .vista(SystemDefinitions.get().vistaSite())
            .build();
    var response =
        TestClients.rpcRequest(SystemDefinitions.get().charon().apiPath() + "v1/rpc", body)
            .expect(403)
            .expectValid(ErrorResponseV1.class);
  }
}
