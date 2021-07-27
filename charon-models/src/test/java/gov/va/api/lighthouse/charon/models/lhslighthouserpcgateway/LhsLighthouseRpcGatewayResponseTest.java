package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.UNSPECIFIED_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.FilemanEntry;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.Results;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.ResultsError;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.UnexpectedVistaValue;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.Values;
import java.util.Map;
import org.junit.jupiter.api.Test;

class LhsLighthouseRpcGatewayResponseTest {

  @Test
  void collectErrors() {
    assertThat(LhsLighthouseRpcGatewayResponse.builder().build().collectErrors()).isEmpty();
    assertThat(
            LhsLighthouseRpcGatewayResponse.builder()
                .resultsByStation(
                    Map.of("1", Results.builder().build(), "2", Results.builder().build()))
                .build()
                .collectErrors())
        .isEmpty();
    assertThat(
            LhsLighthouseRpcGatewayResponse.builder()
                .resultsByStation(
                    Map.of(
                        "1",
                        Results.builder().build(),
                        "2",
                        Results.builder().error(ResultsError.builder().build()).build(),
                        "3",
                        Results.builder()
                            .error(ResultsError.builder().error("three").build())
                            .build()))
                .build()
                .collectErrors())
        .isEqualTo(Map.of("2", UNSPECIFIED_ERROR, "3", "three"));
  }

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

    e.fields().put("where", Values.of("x", "i"));

    var enumeration = Map.of("i", "INTERNAL", "x", "EXTERNAL");
    assertThat(e.internal("where", enumeration).get()).isEqualTo("INTERNAL");
    assertThat(e.external("where", enumeration).get()).isEqualTo("EXTERNAL");
    assertThat(e.internal("who?", enumeration)).isEmpty();
    assertThat(e.external("who?", enumeration)).isEmpty();

    assertThatExceptionOfType(UnexpectedVistaValue.class)
        .isThrownBy(() -> e.internal("there", Integer::valueOf));
    assertThatExceptionOfType(UnexpectedVistaValue.class)
        .isThrownBy(() -> e.external("there", Integer::valueOf));

    e.fields().put("why?!", Values.of("i", "internal"));
    assertThatExceptionOfType(UnexpectedVistaValue.class)
        .isThrownBy(() -> e.internal("why?!", Map.of("xWTF", "iWTF")));
  }
}
