package gov.va.api.lighthouse.charon.models.lhscheckoptionaccess;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LhsCheckOptionAccessTest {
  @Test
  void asDetails() {
    assertThat(
            LhsCheckOptionAccess.Request.builder()
                .duz("DUZ")
                .menuOption("MENUOPTION")
                .build()
                .asDetails())
        .isEqualTo(
            RpcDetails.builder()
                .name("LHS CHECK OPTION ACCESS")
                .context("LHS RPC CONTEXT")
                .parameters(
                    List.of(
                        RpcDetails.Parameter.builder().string("DUZ").build(),
                        RpcDetails.Parameter.builder().string("MENUOPTION").build()))
                .build());
  }
}
