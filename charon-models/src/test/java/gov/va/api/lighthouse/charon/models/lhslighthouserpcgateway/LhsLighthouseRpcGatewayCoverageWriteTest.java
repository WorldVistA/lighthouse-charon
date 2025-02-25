package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayCoverageWrite.Request.CoverageWriteApi;
import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGatewayCoverageWrite.WriteableFilemanValue;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayCoverageWriteTest {
  @Test
  void filemanValueToString() {
    var sample =
        WriteableFilemanValue.builder()
            .file("355.3")
            .index(1)
            .field("#.01")
            .value("SHANKTOPUS")
            .build();
    var expected = "355.3^1^#.01^SHANKTOPUS";
    assertThat(sample.toString()).isEqualTo(expected);
  }

  @Test
  void requestAsDetailsWithCreateApi() {
    var sample =
        LhsLighthouseRpcGatewayCoverageWrite.Request.builder()
            .api(CoverageWriteApi.CREATE)
            .fields(
                Set.of(
                    WriteableFilemanValue.builder()
                        .file("355.3")
                        .index(1)
                        .field("#.01")
                        .value("SHANKTOPUS")
                        .build()))
            .build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayCoverageWrite.RPC_NAME)
            .context(LhsLighthouseRpcGatewayCoverageWrite.DEFAULT_RPC_CONTEXT)
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(
                            List.of(
                                "debugmode^1", "api^create^coverage", "355.3^1^#.01^SHANKTOPUS"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }

  @Test
  void requestAsDetailsWithUpdateApi() {
    var sample =
        LhsLighthouseRpcGatewayCoverageWrite.Request.builder()
            .api(CoverageWriteApi.UPDATE)
            .patient(PatientId.forIcn("10V6"))
            .fields(
                Set.of(
                    WriteableFilemanValue.builder()
                        .file("355.3")
                        .index(1)
                        .field("#.01")
                        .value("SHANKTOPUS")
                        .build()))
            .build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayCoverageWrite.RPC_NAME)
            .context(LhsLighthouseRpcGatewayCoverageWrite.DEFAULT_RPC_CONTEXT)
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(
                            List.of(
                                "debugmode^1",
                                "api^update^coverage",
                                "lhsdfn^:10V6",
                                "355.3^1^#.01^SHANKTOPUS"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }
}
