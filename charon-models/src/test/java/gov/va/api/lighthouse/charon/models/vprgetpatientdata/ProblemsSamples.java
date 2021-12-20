package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "create")
public class ProblemsSamples {
  public List<Problems.Problem> problemResults() {
    return List.of(
        Problems.Problem.builder()
            .acuity(CodeAndNameXmlAttribute.of("C", "CHRONIC"))
            .codingSystem(ValueOnlyXmlAttribute.of("ICD"))
            .comment(
                Problems.Comment.builder()
                    .id("333333")
                    .enteredBy("PROVIDER,ONE")
                    .entered("3100106")
                    .commentText("comment")
                    .build())
            .entered(ValueOnlyXmlAttribute.of("3110302"))
            .exposure(List.of(ValueOnlyXmlAttribute.of("AO")))
            .facility(CodeAndNameXmlAttribute.of("673", "TAMPA (JAH VAH)"))
            .icd(ValueOnlyXmlAttribute.of("401.9"))
            .icdd(ValueOnlyXmlAttribute.of("UNSPECIFIED ESSENTIAL HYPERTENSION"))
            .id(ValueOnlyXmlAttribute.of("886"))
            .location(ValueOnlyXmlAttribute.of("PRIMARY CARE"))
            .name(ValueOnlyXmlAttribute.of("HTN (ICD-9-CM 401.9)"))
            .onset(ValueOnlyXmlAttribute.of("3100106"))
            .provider(CodeAndNameXmlAttribute.of("983", "PROVIDER,ONE"))
            .removed(ValueOnlyXmlAttribute.of("0"))
            .resolved(ValueOnlyXmlAttribute.of("3110302"))
            .sc(ValueOnlyXmlAttribute.of("0"))
            .sctc(ValueOnlyXmlAttribute.of("48694002"))
            .sctd(ValueOnlyXmlAttribute.of("81133019"))
            .sctt(ValueOnlyXmlAttribute.of("Anxiety"))
            .service(ValueOnlyXmlAttribute.of("MEDICINE"))
            .status(CodeAndNameXmlAttribute.of("A", "ACTIVE"))
            .unverified(ValueOnlyXmlAttribute.of("0"))
            .updated(ValueOnlyXmlAttribute.of("3100406"))
            .build());
  }

  public Problems problems() {
    return Problems.builder().total(1).problemResults(problemResults()).build();
  }
}
