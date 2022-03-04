package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.PatientId.forDfn;
import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.PatientId.forIcn;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayCoverageEligibilityResponseTest {

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
