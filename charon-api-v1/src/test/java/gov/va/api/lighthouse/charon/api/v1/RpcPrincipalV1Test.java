package gov.va.api.lighthouse.charon.api.v1;

import static gov.va.api.lighthouse.charon.api.v1.RoundTrip.assertRoundTrip;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalV1.LoginType;
import org.junit.jupiter.api.Test;

public class RpcPrincipalV1Test {
  @Test
  void roundTrip() {
    var sample = RpcPrincipalV1.builder().accessCode("ac").verifyCode("vc").build();
    assertRoundTrip(sample);
  }

  @Test
  void type() {
    assertThat(RpcPrincipalV1.builder().accessCode("ac").verifyCode("").build().type())
        .isEqualTo(LoginType.INVALID);
    assertThat(RpcPrincipalV1.forAvCodes().accessCode("ac").verifyCode("vc").build().type())
        .isEqualTo(LoginType.AV_CODES);
    assertThat(
            RpcPrincipalV1.forApplicationProxyUser()
                .accessCode("ac")
                .verifyCode("vc")
                .applicationProxyUser("ap")
                .build()
                .type())
        .isEqualTo(LoginType.APPLICATION_PROXY_USER);
  }
}
