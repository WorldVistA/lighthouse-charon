package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static gov.va.api.lighthouse.charon.models.vprgetpatientdata.VprGetPatientDataAsserts.assertDeserializedEquals;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class VisitsTest {
  VisitsSamples visitsSamples = VisitsSamples.create();

  @Test
  public static Stream<Arguments> isNotEmpty() {
    return Stream.of(
        Arguments.of(Visits.Visit.builder().build(), false),
        Arguments.of(
            Visits.Visit.builder()
                .cpt(List.of(CodeAndNameXmlAttribute.of("10000", "DRAINAGE OF SKIN LESION")))
                .build(),
            true));
  }

  @SneakyThrows
  @Test
  void deserialize() {
    assertDeserializedEquals(
        "/SampleVisitsResult.xml",
        VprGetPatientData.Response.Results.builder()
            .version("1.13")
            .timeZone("-0500")
            .visits(visitsSamples.visits())
            .build());
  }

  @ParameterizedTest
  @MethodSource
  void isNotEmpty(Visits.Visit visit, boolean expected) {
    assertThat(visit.isNotEmpty()).isEqualTo(expected);
  }

  @Test
  void visitsStream() {
    assertThat(VprGetPatientData.Response.Results.builder().build().visitsStream()).isEmpty();
    var sample =
        VprGetPatientDataSamples.Response.create()
            .resultsByStation(visitsSamples.visits())
            .get("673")
            .visitsStream()
            .collect(Collectors.toList());
    assertThat(sample).isEqualTo(visitsSamples.visitResults());
  }
}
