package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class LhsLighthouseRpcGatewayListGetsManifestTest {

  @Test
  void lazyInitialization() {
    var sample = LhsLighthouseRpcGatewayListGetsManifest.Request.builder().file("1").build();
    assertThat(sample.iens()).isEmpty();
    assertThat(sample.fields()).isEmpty();
    assertThat(sample.number()).isEmpty();
    assertThat(sample.from()).isEmpty();
    assertThat(sample.part()).isEmpty();
    assertThat(sample.index()).isEmpty();
    assertThat(sample.screen()).isEmpty();
    assertThat(sample.from()).isEmpty();
    assertThat(sample.id()).isEmpty();
  }

  @Test
  void requestAsDetails() {
    var sample =
        LhsLighthouseRpcGatewayListGetsManifest.Request.builder()
            .file("2")
            .iens(Optional.of("1"))
            .fields(List.of("#.01", "2.02", "17*"))
            .number(Optional.of("15"))
            .from(
                Optional.of(
                    LhsLighthouseRpcGatewayListManifest.Request.From.builder()
                        .name("NAME")
                        .ien("1")
                        .build()))
            .part(Optional.of("5"))
            .index(Optional.of("2"))
            .screen(Optional.of("3"))
            .id(Optional.of("4"))
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
                                "api^manifest^listgets",
                                "param^FILE^literal^2",
                                "param^IENS^literal^1",
                                "param^FIELDS^literal^.01;2.02;17*",
                                "param^NUMBER^literal^15",
                                "param^FROM^list^1^NAME",
                                "param^FROM^list^2^1",
                                "param^FROM^list^IEN^1",
                                "param^PART^literal^5",
                                "param^INDEX^literal^2",
                                "param^SCREEN^literal^3",
                                "param^ID^literal^4"))
                        .build()))
            .build();
    assertThat(sample.asDetails()).isEqualTo(expected);
  }
}
