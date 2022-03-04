package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeVistaIsAvailable;

import gov.va.api.lighthouse.charon.api.v1.ErrorResponseV1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class BadRequestIT {

  @Test
  @SneakyThrows
  void requestInvalidBodyResponseWith400() {
    assumeVistaIsAvailable();
    String body = "{\"message\": \"Im a malformed request.\"}";
    var response =
        TestClients.charon()
            .post(
                TestClients.headers(), SystemDefinitions.get().charon().apiPath() + "v1/rpc", body)
            .expect(400)
            .expectValid(ErrorResponseV1.class);
    log.info(response.toString());
  }
}
