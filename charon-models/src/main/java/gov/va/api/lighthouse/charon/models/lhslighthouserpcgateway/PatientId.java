package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static org.apache.commons.lang3.StringUtils.isBlank;

import lombok.Value;

/** Patient ID model. */
@Value
public class PatientId {
  String dfn;
  String icn;

  /** You must specify dfn or icn. */
  private PatientId(String dfn, String icn) {
    this.dfn = dfn;
    this.icn = icn;
  }

  public static PatientId forDfn(String dfn) {
    return new PatientId(dfn, null);
  }

  public static PatientId forIcn(String icn) {
    return new PatientId(null, icn);
  }

  @Override
  public String toString() {
    return isBlank(dfn()) ? ":" + icn() : dfn() + ":";
  }
}
