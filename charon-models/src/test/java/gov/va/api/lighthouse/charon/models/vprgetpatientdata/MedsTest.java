package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static gov.va.api.lighthouse.charon.models.vprgetpatientdata.VprGetPatientDataAsserts.assertDeserializedEquals;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MedsTest {
  MedsSamples medsSamples = MedsSamples.create();

  @Test
  public static Stream<Arguments> isNotEmpty() {
    return Stream.of(
        Arguments.of(Meds.Med.builder().build(), false),
        Arguments.of(
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
                .build(),
            true),
        Arguments.of(Meds.Med.builder().daysSupply(ValueOnlyXmlAttribute.of("90")).build(), true),
        Arguments.of(
            Meds.Med.builder()
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
                .build(),
            true),
        Arguments.of(Meds.Med.builder().expires(ValueOnlyXmlAttribute.of("3110514")).build(), true),
        Arguments.of(
            Meds.Med.builder()
                .facility(CodeAndNameXmlAttribute.of("673", "TAMPA (JAH VAH)"))
                .build(),
            true),
        Arguments.of(
            Meds.Med.builder()
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
                .build(),
            true),
        Arguments.of(Meds.Med.builder().fillCost(ValueOnlyXmlAttribute.of(".558")).build(), true),
        Arguments.of(Meds.Med.builder().fillsAllowed(ValueOnlyXmlAttribute.of("3")).build(), true),
        Arguments.of(
            Meds.Med.builder().fillsRemaining(ValueOnlyXmlAttribute.of("2")).build(), true),
        Arguments.of(Meds.Med.builder().form(ValueOnlyXmlAttribute.of("TAB")).build(), true),
        Arguments.of(Meds.Med.builder().id(ValueOnlyXmlAttribute.of("33445")).build(), true),
        Arguments.of(
            Meds.Med.builder().lastFilled(ValueOnlyXmlAttribute.of("3110507")).build(), true),
        Arguments.of(
            Meds.Med.builder().location(CodeAndNameXmlAttribute.of("32", "PRIMARY CARE")).build(),
            true),
        Arguments.of(Meds.Med.builder().medId(ValueOnlyXmlAttribute.of("404201;O")).build(), true),
        Arguments.of(
            Meds.Med.builder().name(ValueOnlyXmlAttribute.of("ACETAMINOPHEN TAB")).build(), true),
        Arguments.of(Meds.Med.builder().orderId(ValueOnlyXmlAttribute.of("33445")).build(), true),
        Arguments.of(
            Meds.Med.builder().ordered(ValueOnlyXmlAttribute.of("3110301.113436")).build(), true),
        Arguments.of(
            Meds.Med.builder()
                .orderingProvider(
                    Meds.Med.Provider.builder()
                        .code("983")
                        .name("PROVIDER,ONE")
                        .taxonomyCode("203B00000N")
                        .providerType("Physicians (M.D. and D.O.)")
                        .classification("Physician/Osteopath")
                        .service("MEDICINE")
                        .build())
                .build(),
            true),
        Arguments.of(
            Meds.Med.builder()
                .pharmacist(CodeAndNameXmlAttribute.of("10000000056", "PHARMACIST,ONE"))
                .build(),
            true),
        Arguments.of(
            Meds.Med.builder().prescription(ValueOnlyXmlAttribute.of("500979")).build(), true),
        Arguments.of(
            Meds.Med.builder()
                .product(
                    List.of(
                        Meds.Med.Product.builder()
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
                .build(),
            true),
        Arguments.of(Meds.Med.builder().quantity(ValueOnlyXmlAttribute.of("90")).build(), true),
        Arguments.of(Meds.Med.builder().routing(ValueOnlyXmlAttribute.of("W")).build(), true),
        Arguments.of(Meds.Med.builder().sig("TAKE ONE TABLET BY MOUTH EVERY DAY").build(), true),
        Arguments.of(Meds.Med.builder().start(ValueOnlyXmlAttribute.of("3080728")).build(), true),
        Arguments.of(
            Meds.Med.builder().status(ValueOnlyXmlAttribute.of("not active")).build(), true),
        Arguments.of(Meds.Med.builder().stop(ValueOnlyXmlAttribute.of("3110507")).build(), true),
        Arguments.of(
            Meds.Med.builder().type(ValueOnlyXmlAttribute.of("Prescription")).build(), true),
        Arguments.of(
            Meds.Med.builder().vaStatus(ValueOnlyXmlAttribute.of("DISCONTINUED")).build(), true),
        Arguments.of(Meds.Med.builder().vaType(ValueOnlyXmlAttribute.of("O")).build(), true));
  }

  @SneakyThrows
  @Test
  void deserialize() {
    assertDeserializedEquals(
        "/SampleMedsResult.xml",
        VprGetPatientData.Response.Results.builder()
            .version("1.13")
            .timeZone("-0500")
            .meds(medsSamples.meds())
            .build());
  }

  @ParameterizedTest
  @MethodSource
  void isNotEmpty(Meds.Med med, boolean expected) {
    assertThat(med.isNotEmpty()).isEqualTo(expected);
  }

  @Test
  void medsStream() {
    assertThat(VprGetPatientData.Response.Results.builder().build().medStream()).isEmpty();
    var sample =
        VprGetPatientDataSamples.Response.create()
            .resultsByStation(medsSamples.meds())
            .get("673")
            .medStream()
            .collect(Collectors.toList());
    assertThat(sample).isEqualTo(medsSamples.medResults());
  }
}
