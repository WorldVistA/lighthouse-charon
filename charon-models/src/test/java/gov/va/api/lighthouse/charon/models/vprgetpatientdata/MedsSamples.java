package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "create")
public class MedsSamples {
  public List<Meds.Med> medResults() {
    return List.of(
        Meds.Med.builder()
            .currentProvider(
                Meds.Med.Provider.builder()
                    .code("983")
                    .name("PROVIDER,ONE")
                    .taxonomyCode("203B00000N")
                    .providerType("Physicians (M.D. and D.O.)")
                    .classification("Physician/Osteopath")
                    .service("MEDICINE")
                    .build())
            .daysSupply(ValueOnlyXmlAttribute.of("90"))
            .dose(
                List.of(
                    Meds.Med.Dose.builder()
                        .dose("500")
                        .units("MG")
                        .unitsPerDose("1")
                        .noun("TABLET")
                        .route("PO")
                        .schedule("QD")
                        .doseStart("3080728")
                        .doseStop("3110507")
                        .build()))
            .expires(ValueOnlyXmlAttribute.of("3110514"))
            .facility(CodeAndNameXmlAttribute.of("673", "TAMPA (JAH VAH)"))
            .fill(
                List.of(
                    Meds.Med.Fill.builder()
                        .fillDate("3080728")
                        .fillRouting("W")
                        .releaseDate("3080728")
                        .fillQuantity("90")
                        .fillDaysSupply("90")
                        .build(),
                    Meds.Med.Fill.builder()
                        .fillDate("3110507")
                        .fillRouting("M")
                        .fillQuantity("90")
                        .fillDaysSupply("90")
                        .build()))
            .fillCost(ValueOnlyXmlAttribute.of(".558"))
            .fillsAllowed(ValueOnlyXmlAttribute.of("3"))
            .fillsRemaining(ValueOnlyXmlAttribute.of("2"))
            .form(ValueOnlyXmlAttribute.of("TAB"))
            .id(ValueOnlyXmlAttribute.of("33445"))
            .lastFilled(ValueOnlyXmlAttribute.of("3110507"))
            .location(CodeAndNameXmlAttribute.of("32", "PRIMARY CARE"))
            .medId(ValueOnlyXmlAttribute.of("404201;O"))
            .name(ValueOnlyXmlAttribute.of("ACETAMINOPHEN TAB"))
            .orderId(ValueOnlyXmlAttribute.of("33445"))
            .ordered(ValueOnlyXmlAttribute.of("3110301.113436"))
            .orderingProvider(
                Meds.Med.Provider.builder()
                    .code("983")
                    .name("PROVIDER,ONE")
                    .taxonomyCode("203B00000N")
                    .providerType("Physicians (M.D. and D.O.)")
                    .classification("Physician/Osteopath")
                    .service("MEDICINE")
                    .build())
            .pharmacist(CodeAndNameXmlAttribute.of("10000000056", "PHARMACIST,ONE"))
            .prescription(ValueOnlyXmlAttribute.of("500979"))
            .product(
                List.of(
                    Meds.Med.Product.builder()
                        .code("213")
                        .name("ACETAMINOPHEN 500MG TAB")
                        .role("D")
                        .concentration("500 MG")
                        .ordItem(CodeAndNameXmlAttribute.of("213", "ACETAMINOPHEN 500MG TAB"))
                        .clazz(
                            Meds.Med.Product.ProductDetail.builder()
                                .code("CN103")
                                .name("NON-OPIOID ANALGESICS")
                                .vuid("4021582")
                                .build())
                        .vaGeneric(
                            Meds.Med.Product.ProductDetail.builder()
                                .code("1338")
                                .name("ACETAMINOPHEN")
                                .vuid("4017513")
                                .build())
                        .vaProduct(
                            Meds.Med.Product.ProductDetail.builder()
                                .code("6638")
                                .name("ACETAMINOPHEN 500MG TAB")
                                .vuid("4007154")
                                .build())
                        .build()))
            .quantity(ValueOnlyXmlAttribute.of("90"))
            .routing(ValueOnlyXmlAttribute.of("W"))
            .sig("TAKE ONE TABLET BY MOUTH EVERY DAY")
            .start(ValueOnlyXmlAttribute.of("3080728"))
            .status(ValueOnlyXmlAttribute.of("not active"))
            .stop(ValueOnlyXmlAttribute.of("3110507"))
            .type(ValueOnlyXmlAttribute.of("Prescription"))
            .vaStatus(ValueOnlyXmlAttribute.of("DISCONTINUED"))
            .vaType(ValueOnlyXmlAttribute.of("O"))
            .build());
  }

  public Meds meds() {
    return Meds.builder().total(1).medResults(medResults()).build();
  }
}
