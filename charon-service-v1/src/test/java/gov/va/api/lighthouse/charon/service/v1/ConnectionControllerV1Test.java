package gov.va.api.lighthouse.charon.service.v1;

import static gov.va.api.lighthouse.charon.service.v1.Samples.connectionDetail;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConnectionControllerV1Test {

  @Test
  void propertiesAreReturned() {
    var properties =
        VistalinkProperties.builder()
            .vistas(List.of(connectionDetail(1), connectionDetail(2)))
            .build();
    var controller = new ConnectionControllerV1(properties);
    assertThat(controller.connections()).isEqualTo(properties);
  }
}
