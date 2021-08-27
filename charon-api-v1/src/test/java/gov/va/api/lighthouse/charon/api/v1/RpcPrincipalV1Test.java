package gov.va.api.lighthouse.charon.api.v1;

import static gov.va.api.lighthouse.charon.api.v1.RoundTrip.assertRoundTrip;

import org.junit.jupiter.api.Test;

public class RpcPrincipalV1Test {
  @Test
  void roundTrip() {
    assertRoundTrip(RpcPrincipalV1.forAvCodes().accessCode("ac").verifyCode("vc").build());
    assertRoundTrip(
        RpcPrincipalV1.forApplicationProxyUser()
            .applicationProxyUser("apu")
            .accessCode("ac")
            .verifyCode("vc")
            .build());
    assertRoundTrip(RpcPrincipalV1.builder().accessCode("ac").verifyCode("vc").build());
  }
}
