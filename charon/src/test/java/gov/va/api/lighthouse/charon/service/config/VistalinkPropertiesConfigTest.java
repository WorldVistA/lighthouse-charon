package gov.va.api.lighthouse.charon.service.config;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import org.junit.jupiter.api.Test;

public class VistalinkPropertiesConfigTest {

  @Test
  void loadParsesVistalinkPropertiesFromFile() {
    VistalinkProperties vp =
        new VistalinkPropertiesConfig().load("src/test/resources/vistalink.properties");
    assertThat(vp.vistas())
        .containsExactlyInAnyOrder(
            ConnectionDetails.builder()
                .name("test")
                .host("testhost")
                .port(1111)
                .divisionIen("testdivisionien")
                .timezone("America/Phoenix")
                .build(),
            ConnectionDetails.builder()
                .name("dummy")
                .host("dummyhost")
                .port(2222)
                .divisionIen("dummydivisionien")
                .timezone("America/Chicago")
                .build(),
            ConnectionDetails.builder()
                .name("fake")
                .host("fakehost")
                .port(3333)
                .divisionIen("fakedivisionien")
                .timezone("America/St_Johns")
                .build());
  }
}
