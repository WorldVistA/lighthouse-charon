package gov.va.api.lighthouse.charon.api.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import gov.va.api.lighthouse.charon.api.v1.RpcDetailsV1.Parameter;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RpcDetailsV1Test {

  static Stream<Arguments> emptyStringsAreParsedFromJsonAsEmptyStrings() {
    return Stream.of(
        Arguments.of(
            "{\"name\":\"MICKY\",\"context\":\"SO FINE\",\"parameters\":[{\"string\":\"\"}]}",
            RpcDetailsV1.builder()
                .name("MICKY")
                .context("SO FINE")
                .parameters(List.of(Parameter.builder().string("").build()))
                .build()),
        Arguments.of(
            "{\"name\":\"MICKY\",\"context\":\"SO FINE\",\"parameters\":[{\"ref\":\"\"}]}",
            RpcDetailsV1.builder()
                .name("MICKY")
                .context("SO FINE")
                .parameters(List.of(Parameter.builder().ref("").build()))
                .build()),
        Arguments.of(
            "{\"name\":\"MICKY\",\"context\":\"SO FINE\",\"parameters\":[{\"array\":[\"a\",\"\"]}]}",
            RpcDetailsV1.builder()
                .name("MICKY")
                .context("SO FINE")
                .parameters(List.of(Parameter.builder().array(List.of("a", "")).build()))
                .build()));
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource
  void emptyStringsAreParsedFromJsonAsEmptyStrings(String json, RpcDetailsV1 expected) {
    RpcDetailsV1 details = JacksonConfig.createMapper().readValue(json, RpcDetailsV1.class);
    assertThat(details).isEqualTo(expected);
  }

  @Test
  void parameterToStringReturnsClassAndType() {
    assertThat(RpcDetailsV1.Parameter.builder().ref("ONE FUGAZI").build().toString())
        .isEqualTo("Parameter(ref=ONE FUGAZI)");
  }

  @Test
  void parameterTypeReturnsCorrectType() {
    assertThat(RpcDetailsV1.Parameter.builder().ref("ONE FUGAZI").build().type()).isEqualTo("ref");
    assertThat(RpcDetailsV1.Parameter.builder().string("TWO FUGAZI").build().type())
        .isEqualTo("string");
    assertThat(
            RpcDetailsV1.Parameter.builder()
                .array(List.of("RED FUGAZI", "BLUE FUGAZI"))
                .build()
                .type())
        .isEqualTo("array");
    assertThat(
            RpcDetailsV1.Parameter.builder()
                .namedArray(Map.of("RED", "RED FUGAZI", "BLUE", "BLUE FUGAZI"))
                .build()
                .type())
        .isEqualTo("array");
  }

  @Test
  void parameterTypeThrowsIllegalStateExceptionOnUnknownType() {
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(
            () -> {
              var ok = RpcDetailsV1.Parameter.builder().string("ok").build();
              var bad = ok.string(null);
              bad.type();
            });
  }

  @Test
  void parameterValueReturnsCorrectValue() {
    assertThat(RpcDetailsV1.Parameter.builder().ref("ONE FUGAZI").build().value())
        .isEqualTo("ONE FUGAZI");
    assertThat(RpcDetailsV1.Parameter.builder().string("TWO FUGAZI").build().value())
        .isEqualTo("TWO FUGAZI");
    assertThat(
            RpcDetailsV1.Parameter.builder()
                .array(List.of("RED FUGAZI", "BLUE FUGAZI"))
                .build()
                .value())
        .isEqualTo(List.of("RED FUGAZI", "BLUE FUGAZI"));
    assertThat(
            RpcDetailsV1.Parameter.builder()
                .namedArray(Map.of("RED", "RED FUGAZI", "BLUE", "BLUE FUGAZI"))
                .build()
                .value())
        .isEqualTo(Map.of("RED", "RED FUGAZI", "BLUE", "BLUE FUGAZI"));
  }

  @Test
  void parameterValueThrowsIllegalStateExceptionOnUnknownType() {
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(
            () -> {
              var ok = RpcDetailsV1.Parameter.builder().string("ok").build();
              var bad = ok.string(null);
              bad.value();
            });
  }

  @Test
  void parametersMustContainOnlyOneType() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () ->
                RpcDetailsV1.builder()
                    .context("rhymes")
                    .name("Dr. Seuss")
                    .parameters(
                        List.of(
                            RpcDetailsV1.Parameter.builder()
                                .ref("ONE FUGAZI")
                                .string("TWO FUGAZI")
                                .array(List.of("RED FUGAZI", "BLUE FUGAZI"))
                                .namedArray(Map.of("RED", "RED FUGAZI", "BLUE", "BLUE FUGAZI"))
                                .build())));
  }
}
