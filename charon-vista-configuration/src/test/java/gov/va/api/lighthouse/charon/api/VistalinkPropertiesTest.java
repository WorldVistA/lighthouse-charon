package gov.va.api.lighthouse.charon.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class VistalinkPropertiesTest {

  @Test
  void names() {
    assertThat(VistalinkProperties.builder().vistas(sampleVistas()).build().names())
        .containsExactlyInAnyOrder("test site 1", "test site 2");
  }

  private List<ConnectionDetails> sampleVistas() {
    return List.of(
        ConnectionDetails.builder().divisionIen("1").name("test site 1").build(),
        ConnectionDetails.builder().divisionIen("2").name("test site 2").build());
  }

  @Test
  void vistas() {
    assertThat(VistalinkProperties.builder().vistas(sampleVistas()).build().vistas())
        .isEqualTo(sampleVistas());
    assertThat(VistalinkProperties.builder().build().vistas()).isEmpty();
  }
}
