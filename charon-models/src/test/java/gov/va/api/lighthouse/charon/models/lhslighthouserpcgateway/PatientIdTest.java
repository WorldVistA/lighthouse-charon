package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PatientIdTest {
  @Test
  void patientIdToString() {
    assertThat(PatientId.forDfn("yyy").toString()).isEqualTo("yyy:");
    assertThat(PatientId.forIcn("zzz").toString()).isEqualTo(":zzz");
  }
}
