package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "create")
public class VisitsSamples {
  public List<Visits.Visit> visitResults() {
    return List.of(
        Visits.Visit.builder()
            .cpt(List.of(CodeAndNameXmlAttribute.of("10000", "DRAINAGE OF SKIN LESION")))
            .creditStopCode(CodeAndNameXmlAttribute.of("706", "ALCOHOL SCREENING"))
            .dateTime(ValueOnlyXmlAttribute.of("3020416.212042"))
            .document(
                List.of(
                    Visits.Document.builder()
                        .id("312")
                        .localTitle("DISCHARGE SUMMARY")
                        .nationalTitle("DISCHARGE SUMMARY")
                        .vuid("4693715")
                        .content("DIAGNOSIS: SANFRANCISCO ADDENDUM PROBLEM")
                        .build()))
            .facility(CodeAndNameXmlAttribute.of("673", "TAMPA (JAH VAH)"))
            .icd(
                List.of(
                    Visits.Icd.builder()
                        .code("391.2")
                        .name("ACUTE RHEUMATIC MYOCARDITIS")
                        .system("ICD")
                        .narrative("ACUTE RHEUMATIC MYOCARDITIS")
                        .ranking("P")
                        .build()))
            .id(ValueOnlyXmlAttribute.of("2552"))
            .location(ValueOnlyXmlAttribute.of("OBSERVATION"))
            .patientClass(ValueOnlyXmlAttribute.of("AMB"))
            .provider(
                List.of(
                    Visits.Provider.builder()
                        .code("11278")
                        .name("WARDCLERK,SIXTYEIGHT")
                        .role("P")
                        .primary("1")
                        .taxonomyCode("203BI0300Y")
                        .providerType("Physicians (M.D. and D.O.)")
                        .classification("Physician/Osteopath")
                        .specialization("Internal Medicine")
                        .service("FISCAL")
                        .build()))
            .reason(
                Visits.Reason.builder()
                    .code("391.2")
                    .name("ACUTE RHEUMATIC MYOCARDITIS")
                    .system("ICD")
                    .narrative("ACUTE RHEUMATIC MYOCARDITIS")
                    .build())
            .service(ValueOnlyXmlAttribute.of("MEDICINE"))
            .serviceCategory(CodeAndNameXmlAttribute.of("A", "AMBULATORY"))
            .type(CodeAndNameXmlAttribute.of("99211", "OFFICE/OUTPATIENT VISIT EST"))
            .visitString(ValueOnlyXmlAttribute.of("289;3020423.192148;A"))
            .admission(ValueOnlyXmlAttribute.of("3608"))
            .arrivalDateTime(ValueOnlyXmlAttribute.of("3020416.212042"))
            .departureDateTime(ValueOnlyXmlAttribute.of("3020416.2141"))
            .ptf(ValueOnlyXmlAttribute.of("893"))
            .roomBed(ValueOnlyXmlAttribute.of("2-5"))
            .specialty(ValueOnlyXmlAttribute.of("GENERAL MEDICINE"))
            .build());
  }

  public Visits visits() {
    return Visits.builder().total(1).visitResults(visitResults()).build();
  }
}
