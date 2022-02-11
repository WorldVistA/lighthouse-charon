package gov.va.api.lighthouse.charon.api.v1;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalsV1.Codes;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalsV1.PrincipalEntry;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class RpcPrincipalsV1Test {
  private Map<String, RpcPrincipalV1> _asian() {
    return Map.of(
        "111",
        RpcPrincipalV1.builder()
            .applicationProxyUser("ASIAN!")
            .accessCode("ASIAN_FOOD")
            .verifyCode("IS_AMAZING")
            .build(),
        "222",
        RpcPrincipalV1.builder()
            .applicationProxyUser("ASIAN!")
            .accessCode("ASIAN_FOOD")
            .verifyCode("IS_AMAZING")
            .build(),
        "111-A",
        RpcPrincipalV1.builder()
            .applicationProxyUser("ASIAN!")
            .accessCode("ASIAN_FOOD")
            .verifyCode("IS_STILL_GREAT")
            .build(),
        "222-A",
        RpcPrincipalV1.builder()
            .applicationProxyUser("ASIAN!")
            .accessCode("ASIAN_FOOD")
            .verifyCode("IS_STILL_GREAT")
            .build());
  }

  private Map<String, RpcPrincipalV1> _italian() {
    return Map.of(
        "666",
        RpcPrincipalV1.builder()
            .applicationProxyUser("ITALIAN!")
            .accessCode("ITALIAN_FOOD")
            .verifyCode("IS_AMAZING")
            .build());
  }

  private Map<String, RpcPrincipalV1> _mexican() {
    return Map.of(
        "111",
        RpcPrincipalV1.builder()
            .applicationProxyUser("MEXICAN!")
            .accessCode("MEXICAN_FOOD")
            .verifyCode("IS_AMAZING")
            .build(),
        "222",
        RpcPrincipalV1.builder()
            .applicationProxyUser("MEXICAN!")
            .accessCode("MEXICAN_FOOD")
            .verifyCode("IS_AMAZING")
            .build(),
        "111-M",
        RpcPrincipalV1.builder()
            .applicationProxyUser("MEXICAN!")
            .accessCode("MEXICAN_FOOD")
            .verifyCode("IS_STILL_GREAT")
            .build(),
        "222-M",
        RpcPrincipalV1.builder()
            .applicationProxyUser("MEXICAN!")
            .accessCode("MEXICAN_FOOD")
            .verifyCode("IS_STILL_GREAT")
            .build());
  }

  @SneakyThrows
  private RpcPrincipalsV1 _testConfig() {
    return new ObjectMapper()
        .readValue(new File("src/test/resources/principals.json"), RpcPrincipalsV1.class);
  }

  private RpcPrincipalLookupV1 _testPrincipals() {
    return RpcPrincipalLookupV1.of(_testConfig());
  }

  @Test
  void findByName() {
    assertThat(_testPrincipals().findByName("TACO")).isEqualTo(_mexican());
    assertThat(_testPrincipals().findByName("NACHO")).isEqualTo(_mexican());
    assertThat(_testPrincipals().findByName("BURRITO")).isEqualTo(_mexican());
    assertThat(_testPrincipals().findByName("STIR-FRY")).isEqualTo(_asian());
    assertThat(_testPrincipals().findByName("SASHIMI")).isEqualTo(_asian());
    assertThat(_testPrincipals().findByName("PIZZA")).isEqualTo(_italian());
    assertThat(_testPrincipals().findByName("SPAGHETTI")).isEqualTo(_italian());
    assertThat(_testPrincipals().findByName("BRUSSELSPROUTS")).isEqualTo(Collections.emptyMap());
    assertThat(_testPrincipals().findByName(null)).isEqualTo(Collections.emptyMap());
  }

  @Test
  void findByNameAndSite() {
    assertThat(_testPrincipals().findByNameAndSite("TACO", "111"))
        .isEqualTo(
            Optional.of(
                RpcPrincipalV1.builder()
                    .applicationProxyUser("MEXICAN!")
                    .accessCode("MEXICAN_FOOD")
                    .verifyCode("IS_AMAZING")
                    .build()));
    assertThat(_testPrincipals().findByNameAndSite("TACO", "111-M"))
        .isEqualTo(
            Optional.of(
                RpcPrincipalV1.builder()
                    .applicationProxyUser("MEXICAN!")
                    .accessCode("MEXICAN_FOOD")
                    .verifyCode("IS_STILL_GREAT")
                    .build()));
    assertThat(_testPrincipals().findByNameAndSite("SASHIMI", "222-A"))
        .isEqualTo(
            Optional.of(
                RpcPrincipalV1.builder()
                    .applicationProxyUser("ASIAN!")
                    .accessCode("ASIAN_FOOD")
                    .verifyCode("IS_STILL_GREAT")
                    .build()));
    assertThat(_testPrincipals().findByNameAndSite("PIZZA", "666"))
        .isEqualTo(
            Optional.of(
                RpcPrincipalV1.builder()
                    .applicationProxyUser("ITALIAN!")
                    .accessCode("ITALIAN_FOOD")
                    .verifyCode("IS_AMAZING")
                    .build()));
    assertThat(_testPrincipals().findByNameAndSite("CHEESECAKE", "NOPE"))
        .isEqualTo(Optional.empty());
    assertThat(_testPrincipals().findByNameAndSite("CHEESECAKE", "111-A"))
        .isEqualTo(Optional.empty());
    assertThat(_testPrincipals().findByNameAndSite("PIZZA", "111-M")).isEqualTo(Optional.empty());
    assertThat(_testPrincipals().findByNameAndSite(null, null)).isEqualTo(Optional.empty());
  }

  @Test
  void findByTag() {
    assertThat(_testPrincipals().findByTag("FRIDAYS")).isEqualTo(_mexican());
    assertThat(_testPrincipals().findByTag("MONDAYS")).isEqualTo(Map.of());
  }

  @Test
  void isRpcNamesUnique() {
    assertThat(
            RpcPrincipalsV1.builder()
                .entries(
                    List.of(
                        RpcPrincipalsV1.PrincipalEntry.builder()
                            .rpcNames(List.of("TACO", "SUSHI", "BEER"))
                            .build(),
                        RpcPrincipalsV1.PrincipalEntry.builder()
                            .rpcNames(List.of("STRAWBERRY", "CURRY", "WINE"))
                            .build()))
                .build()
                .isEachRpcNameUnique())
        .isTrue();
    assertThat(
            RpcPrincipalsV1.builder()
                .entries(
                    List.of(
                        RpcPrincipalsV1.PrincipalEntry.builder()
                            .rpcNames(List.of("TACO", "SUSHI", "BEER"))
                            .build(),
                        RpcPrincipalsV1.PrincipalEntry.builder()
                            .rpcNames(List.of("STRAWBERRY", "CURRY", "WINE", "TACO"))
                            .build()))
                .build()
                .isEachRpcNameUnique())
        .isFalse();
  }

  @Test
  void isSiteUniqueWithinCodes() {
    assertThat(
            RpcPrincipalsV1.PrincipalEntry.builder()
                .codes(
                    List.of(
                        RpcPrincipalsV1.Codes.builder()
                            .sites(List.of("TACO", "SUSHI", "BEER"))
                            .build(),
                        RpcPrincipalsV1.Codes.builder()
                            .sites(List.of("STRAWBERRY", "CURRY", "WINE"))
                            .build()))
                .build()
                .isSitesUniqueWithinCodes())
        .isTrue();
    assertThat(
            RpcPrincipalsV1.PrincipalEntry.builder()
                .codes(
                    List.of(
                        RpcPrincipalsV1.Codes.builder()
                            .sites(List.of("TACO", "SUSHI", "BEER"))
                            .build(),
                        RpcPrincipalsV1.Codes.builder()
                            .sites(List.of("STRAWBERRY", "CURRY", "WINE", "TACO"))
                            .build()))
                .build()
                .isSitesUniqueWithinCodes())
        .isFalse();
  }

  @Test
  void listsAreNeverNull() {
    assertThat(new RpcPrincipalsV1().entries()).isNotNull();
    assertThat(new Codes().sites()).isNotNull();
    assertThat(new PrincipalEntry().rpcNames()).isNotNull();
    assertThat(new PrincipalEntry().codes()).isNotNull();
  }
}
