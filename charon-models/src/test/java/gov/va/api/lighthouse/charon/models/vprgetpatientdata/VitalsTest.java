package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static gov.va.api.lighthouse.charon.models.vprgetpatientdata.VprGetPatientDataAsserts.assertDeserializedEquals;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class VitalsTest {

  VitalsSamples samples = VitalsSamples.create();

  @Test
  public static Stream<Arguments> isNotEmpty() {
    return Stream.of(
        Arguments.of(Vitals.Vital.builder().build(), false),
        Arguments.of(
            Vitals.Vital.builder().entered(ValueOnlyXmlAttribute.builder().build()).build(), true),
        Arguments.of(
            Vitals.Vital.builder().facility(CodeAndNameXmlAttribute.builder().build()).build(),
            true),
        Arguments.of(
            Vitals.Vital.builder().location(CodeAndNameXmlAttribute.builder().build()).build(),
            true),
        Arguments.of(Vitals.Vital.builder().measurements(List.of()).build(), true),
        Arguments.of(
            Vitals.Vital.builder()
                .removed(List.of(ValueOnlyXmlAttribute.builder().build()))
                .build(),
            true),
        Arguments.of(
            Vitals.Vital.builder().taken(ValueOnlyXmlAttribute.builder().build()).build(), true),
        Arguments.of(VitalsSamples.create().vitals().vitalResults().get(0), true));
  }

  @Test
  public void bloodPressure() {
    assertThat(samples.measurements().get(0).asBloodPressure())
        .isEqualTo(Optional.of(samples.bloodPressure()));
    assertThat(samples.measurements().get(1).asBloodPressure()).isEqualTo(Optional.empty());
  }

  @SneakyThrows
  @Test
  public void deserialize() {
    assertDeserializedEquals(
        "/SampleVitalsResult.xml",
        VprGetPatientData.Response.Results.builder()
            .version("1.13")
            .timeZone("-0500")
            .vitals(samples.vitals())
            .build());
  }

  @ParameterizedTest
  @MethodSource
  void isNotEmpty(Vitals.Vital vital, boolean expected) {
    assertThat(vital.isNotEmpty()).isEqualTo(expected);
  }

  @Test
  void vitalStream() {
    assertThat(VprGetPatientData.Response.Results.builder().build().vitalStream()).isEmpty();
    assertThat(samples.response().resultsByStation().get("673").vitalStream())
        .containsExactlyElementsOf(samples.vitals().vitalResults());
  }
}
