package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.PatientId.forDfn;
import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.PatientId.forIcn;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.FilemanEntry;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayResponse.Values;
import io.micrometer.core.instrument.util.IOUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayCoverageEligibilityResponseTest {
  @Test
  void fromResults() {
    var responseString =
        IOUtils.toString(
            getClass().getResourceAsStream("/coverage-eligibility-response-response.json"));
    var sample =
        List.of(
            RpcInvocationResult.builder().vista("777").response(responseString).build(),
            RpcInvocationResult.builder()
                .vista("666")
                .error(Optional.of("Big Oof!"))
                .response("Nah!")
                .build());
    var expected =
        LhsLighthouseRpcGatewayResponse.builder()
            .resultsByStation(
                Map.of(
                    "777",
                    LhsLighthouseRpcGatewayResponse.Results.builder()
                        .results(
                            List.of(
                                FilemanEntry.builder()
                                    .file("36")
                                    .ien("5,")
                                    .status("1")
                                    .fields(Map.of("#.01", Values.of("BCBS OF TX", "BCBS OF TX")))
                                    .build(),
                                FilemanEntry.builder()
                                    .file("365")
                                    .ien("1,")
                                    .status("1")
                                    .fields(Map.of("#.01", Values.of("COV-1234567", "COV-1234567")))
                                    .build(),
                                FilemanEntry.builder()
                                    .file("2.312")
                                    .ien("2,100848,")
                                    .status("1")
                                    .fields(
                                        Map.of(
                                            "#17", Values.of("TESTPAT,SUSAN G", "TESTPAT,SUSAN G")))
                                    .build()))
                        .build()))
            .build();
    assertThat(LhsLighthouseRpcGatewayCoverageEligibilityResponse.create().fromResults(sample))
        .isEqualTo(expected);
  }

  @Test
  void readRequestAsDetailsWithDfn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageEligibilityResponse.Request.read()
            .patientId(forDfn("zzz"))
            .iens("2,666666")
            .build();
    var expected =
        RpcDetails.builder()
            .name("LHS LIGHTHOUSE RPC GATEWAY")
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(
                            List.of(
                                "debugmode^1",
                                "api^read^cer",
                                "lhsdfn^zzz:",
                                InsuranceType.FILE_NUMBER + "^1^IEN^2,666666"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void readRequestAsDetailsWithIcn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageEligibilityResponse.Request.read()
            .patientId(forIcn("yyy"))
            .iens("2,666666")
            .build();
    var expected =
        RpcDetails.builder()
            .name("LHS LIGHTHOUSE RPC GATEWAY")
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(
                            List.of(
                                "debugmode^1",
                                "api^read^cer",
                                "lhsdfn^:yyy",
                                InsuranceType.FILE_NUMBER + "^1^IEN^2,666666"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void searchRequestAsDetailsWithDfn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageEligibilityResponse.Request.search()
            .patientId(forDfn("zzz"))
            .build();
    var expected =
        RpcDetails.builder()
            .name("LHS LIGHTHOUSE RPC GATEWAY")
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(List.of("debugmode^1", "api^search^cer", "lhsdfn^zzz:"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void searchRequestAsDetailsWithIcn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageEligibilityResponse.Request.search()
            .patientId(forIcn("yyy"))
            .build();
    var expected =
        RpcDetails.builder()
            .name("LHS LIGHTHOUSE RPC GATEWAY")
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(List.of("debugmode^1", "api^search^cer", "lhsdfn^:yyy"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }
}
