package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class LhsLighthouseRpcGatewayTest {

  @Test
  void allFieldsOfSubfile() {
    assertThat(LhsLighthouseRpcGateway.allFieldsOfSubfile(".123")).containsExactly(".123*");
    assertThat(LhsLighthouseRpcGateway.allFieldsOfSubfile("#.123")).containsExactly(".123*");
  }

  @Test
  void deoctothorpe() {
    assertThat(LhsLighthouseRpcGateway.deoctothorpe(".123")).isEqualTo(".123");
    assertThat(LhsLighthouseRpcGateway.deoctothorpe("#.123")).isEqualTo(".123");
    assertThat(LhsLighthouseRpcGateway.deoctothorpe("#")).isEqualTo("");
    assertThat(LhsLighthouseRpcGateway.deoctothorpe("")).isEqualTo("");
    assertThat(LhsLighthouseRpcGateway.deoctothorpe(List.of(".123", "#.123", "#", "")))
        .isEqualTo(List.of(".123", ".123", "", ""));
  }
}
