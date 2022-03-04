package gov.va.api.lighthouse.charon.service.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalLookupV1;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalV1;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalsV1;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class RpcPrincipalsConfigTest {

  @Test
  void loadPrincipals() {
    RpcPrincipalLookupV1 testPrincipals =
        new RpcPrincipalConfig().loadPrincipals("src/test/resources/principals.json");
    assertThat(testPrincipals.findByNameAndSite("SASHIMI", "222-A"))
        .isEqualTo(
            Optional.of(
                RpcPrincipalV1.builder()
                    .applicationProxyUser("ASIAN!")
                    .accessCode("ASIAN_FOOD")
                    .verifyCode("IS_STILL_GREAT")
                    .build()));
  }

  @Test
  void loadPrincipalsNullFile() {
    assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy(() -> new RpcPrincipalConfig().loadPrincipals(null));
  }

  @Test
  void validate() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> new RpcPrincipalConfig().validate(RpcPrincipalsV1.builder().build(), "whatever"));
  }
}
