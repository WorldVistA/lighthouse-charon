package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeVistaIsAvailable;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RpcRequestIT {

  @Test
  void requestRpcWithGlobalArrayArgument() {
    requestRpcWithValidResponse(SystemDefinitions.get().testRpcs().globalArrayRequestRpc());
  }

  @Test
  void requestRpcWithLocalArrayArgument() {
    requestRpcWithValidResponse(SystemDefinitions.get().testRpcs().localArrayRequestRpc());
  }

  @Test
  void requestRpcWithStringArgument() {
    requestRpcWithValidResponse(SystemDefinitions.get().testRpcs().stringRequestRpc());
  }

  @SneakyThrows
  void requestRpcWithValidResponse(RpcDetails rpc) {
    assumeVistaIsAvailable();
    log.info(rpc.name());
    RpcRequestV1 body =
        RpcRequestV1.builder()
            .rpc(rpc)
            .principal(SystemDefinitions.get().avCodePrincipal())
            .vista(SystemDefinitions.get().vistaSite())
            .build();
    var response =
        TestClients.rpcRequest(SystemDefinitions.get().charon().apiPath() + "v1/rpc", body)
            .expect(200)
            .expectValid(RpcInvocationResultV1.class);
    log.info(response.toString());
  }
}
