package gov.va.api.lighthouse.charon.models.xobvtestping;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class XobvTestPingTest {
  @Test
  void asDetails() {
    assertThat(XobvTestPing.Request.builder().build().asDetails())
        .isEqualTo(
            RpcDetails.builder().name("XOBV TEST PING").context("XOBV VISTALINK TESTER").build());
    assertThat(XobvTestPing.Request.builder().context(Optional.of("CONTEXT")).build().asDetails())
        .isEqualTo(RpcDetails.builder().name("XOBV TEST PING").context("CONTEXT").build());
  }
}
