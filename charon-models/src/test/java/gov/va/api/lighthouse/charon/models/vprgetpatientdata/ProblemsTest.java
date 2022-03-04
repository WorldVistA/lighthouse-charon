package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static gov.va.api.lighthouse.charon.models.vprgetpatientdata.VprGetPatientDataAsserts.assertDeserializedEquals;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProblemsTest {
  ProblemsSamples problemsSamples = ProblemsSamples.create();

  @Test
  public static Stream<Arguments> isNotEmpty() {
    return Stream.of(
        Arguments.of(Problems.Problem.builder().build(), false),
        Arguments.of(
            Problems.Problem.builder().acuity(CodeAndNameXmlAttribute.of("a", "b")).build(), true),
        Arguments.of(
            Problems.Problem.builder().codingSystem(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder()
                .comment(
                    Problems.Comment.builder()
                        .id("a")
                        .commentText("b")
                        .entered("c")
                        .enteredBy("d")
                        .build())
                .build(),
            true),
        Arguments.of(
            Problems.Problem.builder().entered(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().exposure(List.of(ValueOnlyXmlAttribute.of("a"))).build(),
            true),
        Arguments.of(
            Problems.Problem.builder().facility(CodeAndNameXmlAttribute.of("a", "b")).build(),
            true),
        Arguments.of(Problems.Problem.builder().icd(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().icdd(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().id(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().location(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().name(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().onset(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().provider(CodeAndNameXmlAttribute.of("a", "b")).build(),
            true),
        Arguments.of(
            Problems.Problem.builder().removed(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().sc(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().sctc(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().sctd(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(Problems.Problem.builder().sctt(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().service(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().status(CodeAndNameXmlAttribute.of("a", "b")).build(), true),
        Arguments.of(
            Problems.Problem.builder().unverified(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Problems.Problem.builder().updated(ValueOnlyXmlAttribute.of("a")).build(), true));
  }

  @SneakyThrows
  @Test
  void deserialize() {
    assertDeserializedEquals(
        "/SampleProblemsResult.xml",
        VprGetPatientData.Response.Results.builder()
            .version("1.13")
            .timeZone("-0500")
            .problems(problemsSamples.problems())
            .build());
  }

  @ParameterizedTest
  @MethodSource
  void isNotEmpty(Problems.Problem problem, boolean expected) {
    assertThat(problem.isNotEmpty()).isEqualTo(expected);
  }

  @Test
  void problemsStream() {
    assertThat(VprGetPatientData.Response.Results.builder().build().problemStream()).isEmpty();
    var sample =
        VprGetPatientDataSamples.Response.create()
            .resultsByStation(problemsSamples.problems())
            .get("673")
            .problemStream()
            .collect(Collectors.toList());
    assertThat(sample).isEqualTo(problemsSamples.problemResults());
  }
}
