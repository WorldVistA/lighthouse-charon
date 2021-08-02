package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import io.micrometer.core.instrument.util.IOUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayCoverageSearchTest {
  @Test
  void fromResults() {
    var responseString =
        IOUtils.toString(getClass().getResourceAsStream("/coverage-response.json"));
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
                                LhsLighthouseRpcGatewayResponse.FilemanEntry.builder()
                                    .file("2.312")
                                    .ien("1,69,")
                                    .fields(
                                        Map.of(
                                            "#.01",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "BCBS OF FL", "4"),
                                            "#.18",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "BCBS OF FL", "87"),
                                            "#.2",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "PRIMARY", "1"),
                                            "#3",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "JAN 01, 2025", "3250101"),
                                            "#4.03",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "SPOUSE", "01"),
                                            "#7.02",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "R50797108", "R50797108"),
                                            "#8",
                                            LhsLighthouseRpcGatewayResponse.Values.of(
                                                "JAN 12, 1992", "2920112")))
                                    .build()))
                        .build()))
            .build();
    assertThat(LhsLighthouseRpcGatewayCoverageSearch.create().fromResults(sample))
        .isEqualTo(expected);
  }

  @Test
  void patientIds() {
    assertThat(LhsLighthouseRpcGatewayCoverageSearch.Request.PatientId.forDfn("yyy").toString())
        .isEqualTo("yyy");
    assertThat(LhsLighthouseRpcGatewayCoverageSearch.Request.PatientId.forIcn("zzz").toString())
        .isEqualTo(":zzz");
  }

  @Test
  void requestAsDetailsWithDfn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageSearch.Request.builder()
            .id(LhsLighthouseRpcGatewayCoverageSearch.Request.PatientId.forDfn("zzz"))
            .build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayListManifest.RPC_NAME)
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(List.of("debugmode^1", "api^search^coverage", "lhsdfn^zzz"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void requestAsDetailsWithIcn() {
    var sample =
        LhsLighthouseRpcGatewayCoverageSearch.Request.builder()
            .id(LhsLighthouseRpcGatewayCoverageSearch.Request.PatientId.forIcn("yyy"))
            .build();
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
