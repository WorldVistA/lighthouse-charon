package gov.va.api.lighthouse.charon.service.v1;

import static gov.va.api.lighthouse.charon.service.v1.Samples.connectionDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.UnknownVista;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConfigurableVistaResolverTest {

  ConfigurableVistaResolver _resolver() {
    return new ConfigurableVistaResolver(
        VistalinkProperties.builder()
            .vistas(List.of(connectionDetail(1), connectionDetail(2), connectionDetail(3)))
            .build());
  }

  @Test
  void resolveFromName() {
    assertThat(_resolver().resolve("v2")).isEqualTo(connectionDetail(2));
  }

  @Test
  void resolveFromSpec() {
    var spec = "awesome.com:1234:567:America/New_York";
    assertThat(_resolver().resolve(spec))
        .isEqualTo(
            ConnectionDetails.builder()
                .name(spec)
                .host("awesome.com")
                .port(1234)
                .divisionIen("567")
                .timezone("America/New_York")
                .build());
  }

  @Test
  void resolveThrowsExceptionForUnknownName() {
    assertThatExceptionOfType(UnknownVista.class).isThrownBy(() -> _resolver().resolve("nope"));
  }
}
