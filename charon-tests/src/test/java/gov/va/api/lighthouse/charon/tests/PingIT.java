package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeVistaIsAvailable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class PingIT {
  @Test
  void healthCheckIt() {
    // No test's running causes the entrypoint to fail.
    // In order to test when Vista is available and not fail when it isn't,
    // this will run in all environments.
    var basePath = "/charon/";
    log.warn("Running HealthCheck outside of local environment.");
    var requestPath = basePath + "actuator/health";
    log.info("Running health-check for path: {}", requestPath);
    TestClients.charon().get(requestPath).response().then().body("status", equalTo("UP"));
  }

  @Test
  @SneakyThrows
  void requestRpcNoArguments() {
    assumeVistaIsAvailable();
    RpcRequestV1 body =
        RpcRequestV1.builder()
            .rpc(SystemDefinitions.get().testRpcs().pingRpc())
            .principal(SystemDefinitions.get().avCodePrincipal())
            .vista(SystemDefinitions.get().vistaSite())
            .build();
    var response =
        TestClients.rpcRequest(SystemDefinitions.get().charon().apiPath() + "v1/rpc", body)
            .expect(200)
            .expectValid(RpcInvocationResultV1.class);
    assertThat(response.response()).isNotEmpty();
    log.info(response.toString());
  }
}
