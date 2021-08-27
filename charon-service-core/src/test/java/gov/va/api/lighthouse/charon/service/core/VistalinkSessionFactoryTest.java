package gov.va.api.lighthouse.charon.service.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.IsPrincipal;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

class VistalinkSessionFactoryTest {

  ConnectionDetails _connectionDetails() {
    return ConnectionDetails.builder()
        .name("fugazi")
        .host("fugazi.com")
        .port(666)
        .divisionIen("666")
        .timezone("America/New_York")
        .build();
  }

  @Test
  void sessionSelection() {
    assertThat(
            VistalinkSessionFactory.create(
                FugaziPrincipal.builder().accessCode("a").verifyCode("v").build(),
                _connectionDetails()))
        .isInstanceOf(StandardUserVistalinkSession.class);
    assertThat(
            VistalinkSessionFactory.create(
                FugaziPrincipal.builder()
                    .accessCode("a")
                    .verifyCode("v")
                    .applicationProxyUser("u")
                    .build(),
                _connectionDetails()))
        .isInstanceOf(ApplicationProxyUserVistalinkSession.class);
  }

  @Test
  void sessionThrowsExceptionIfTypeIsUnknown() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () ->
                VistalinkSessionFactory.create(
                    FugaziPrincipal.builder().build(), _connectionDetails()));
  }

  @Data
  @Builder
  private static class FugaziPrincipal implements IsPrincipal {
    String accessCode;
    String verifyCode;
    String applicationProxyUser;
  }
}
