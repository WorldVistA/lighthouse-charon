package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayCoverageSearchTest {

  @Test
  void requestAsDetailsWithDfn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageSearch.Request.builder().id(PatientId.forDfn("zzz")).build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayListManifest.RPC_NAME)
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(List.of("debugmode^1", "api^search^coverage", "lhsdfn^zzz:"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void requestAsDetailsWithIcn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageSearch.Request.builder().id(PatientId.forIcn("yyy")).build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayListManifest.RPC_NAME)
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(List.of("debugmode^1", "api^search^coverage", "lhsdfn^:yyy"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }
}
