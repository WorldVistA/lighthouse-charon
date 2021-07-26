package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.FilemanEntry;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.UnexpectedVistaValue;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.Values;
import org.junit.jupiter.api.Test;

class LhsLighthouseRpcGatewayResponseTest {

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  void entryValueHelpers() {
    var e = FilemanEntry.builder().build();
    assertThat(e.fields()).isEmpty();
    assertThat(e.field("nope")).isNull();
    assertThat(e.internal("nope")).isEmpty();
    assertThat(e.external("nope")).isEmpty();
    assertThat(e.internal("nope", Integer::valueOf)).isEmpty();
    assertThat(e.external("nope", Integer::valueOf)).isEmpty();

    e.fields().put("here", Values.of("1", "2"));
    e.fields().put("there", Values.of("a", "b"));

    assertThat(e.field("here")).isEqualTo(Values.of("1", "2"));
    assertThat(e.internal("here").get()).isEqualTo("2");
    assertThat(e.internal("here", Integer::valueOf).get()).isEqualTo(2);
    assertThat(e.external("here", Integer::valueOf).get()).isEqualTo(1);

    assertThatExceptionOfType(UnexpectedVistaValue.class)
        .isThrownBy(() -> e.internal("there", Integer::valueOf));
    assertThatExceptionOfType(UnexpectedVistaValue.class)
        .isThrownBy(() -> e.external("there", Integer::valueOf));
  }
}
