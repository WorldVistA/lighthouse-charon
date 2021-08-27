package gov.va.api.lighthouse.charon.api;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.IsPrincipal.LoginType;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

class IsPrincipalTest {

  @Test
  void type() {
    assertThat(FugaziPrincipal.builder().accessCode("ac").verifyCode("").build().type())
        .isEqualTo(LoginType.INVALID);
    assertThat(FugaziPrincipal.builder().accessCode("ac").verifyCode("vc").build().type())
        .isEqualTo(LoginType.AV_CODES);
    assertThat(
            FugaziPrincipal.builder()
                .accessCode("ac")
                .verifyCode("vc")
                .applicationProxyUser("ap")
                .build()
                .type())
        .isEqualTo(LoginType.APPLICATION_PROXY_USER);
  }

  @Data
  @Builder
  private static class FugaziPrincipal implements IsPrincipal {
    String accessCode;
    String verifyCode;
    String applicationProxyUser;
  }
}
