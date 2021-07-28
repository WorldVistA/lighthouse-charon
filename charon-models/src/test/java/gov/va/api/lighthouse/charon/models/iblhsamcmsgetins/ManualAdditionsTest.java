package gov.va.api.lighthouse.charon.models.iblhsamcmsgetins;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.InsuranceCompany;
import org.junit.jupiter.api.Test;

public class ManualAdditionsTest {

  @Test
  void manualAdditions() {
    assertThat(InsuranceCompany.INACTIVE).isEqualTo("#.05");
  }
}
