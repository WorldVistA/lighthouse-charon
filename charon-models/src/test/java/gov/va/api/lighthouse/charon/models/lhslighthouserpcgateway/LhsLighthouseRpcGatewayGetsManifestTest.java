package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayGetsManifestTest {
  @Test
  void lazyInitialization() {
    var sample =
        LhsLighthouseRpcGatewayGetsManifest.Request.builder()
            .file("1")
            .iens("2")
            .fields(List.of("3"))
            .build();
    assertThat(sample.flags()).isEmpty();
  }

  @Test
  void requestAsDetails() {
    var sample =
        LhsLighthouseRpcGatewayGetsManifest.Request.builder()
            .file("2")
            .iens("1")
            .fields(List.of("#.01", ".3121*"))
            .flags(
                List.of(
                    LhsLighthouseRpcGatewayGetsManifest.Request.GetsManifestFlags.OMIT_NULL_VALUES,
                    LhsLighthouseRpcGatewayGetsManifest.Request.GetsManifestFlags
                        .RETURN_INTERNAL_VALUES,
                    LhsLighthouseRpcGatewayGetsManifest.Request.GetsManifestFlags
                        .RETURN_EXTERNAL_VALUES))
            .build();
    var expected =
        RpcDetails.builder()
            .name(LhsLighthouseRpcGatewayListManifest.RPC_NAME)
            .context("LHS RPC CONTEXT")
            .parameters(
                List.of(
                    RpcDetails.Parameter.builder()
                        .array(
                            List.of(
                                "debugmode^1",
                                "api^manifest^gets",
                                "param^FILE^literal^2",
                                "param^IENS^literal^1",
                                "param^FIELDS^literal^.01;.3121*",
                                "param^FLAGS^literal^NIE"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }
}
