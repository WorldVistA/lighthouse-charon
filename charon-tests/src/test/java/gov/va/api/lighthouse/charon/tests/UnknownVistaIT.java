package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeVistaIsAvailable;

import gov.va.api.lighthouse.charon.api.v1.ErrorResponseV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnknownVistaIT {
  @Test
  @SneakyThrows
  void requestUnkonwnVistaWith400() {
    assumeVistaIsAvailable();
    RpcRequestV1 body =
        RpcRequestV1.builder()
            .rpc(SystemDefinitions.get().testRpcs().pingRpc())
            .principal(SystemDefinitions.get().avCodePrincipal())
            .vista("who dis")
            .build();
    var response =
        TestClients.rpcRequest(SystemDefinitions.get().charon().apiPath() + "v1/rpc", body)
            .expect(400)
            .expectValid(ErrorResponseV1.class);
    log.info(response.toString());
  }
}
