package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Container for vista visits models. */
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Visits {

  @JacksonXmlProperty(isAttribute = true)
  Integer total;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "visit")
  List<Visits.Visit> visitResults;

  /** Lazy Initializer. */
  public List<Visits.Visit> visitResults() {
    if (visitResults == null) {
      visitResults = new ArrayList<>();
    }
    return visitResults;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Visit {
    private static final Visits.Visit EMPTY = new Visits.Visit();

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "cpts")
    List<CodeAndNameXmlAttribute> cpt;

    @JacksonXmlProperty CodeAndNameXmlAttribute creditStopCode;
    @JacksonXmlProperty ValueOnlyXmlAttribute dateTime;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "documents")
    List<Document> document;

    @JacksonXmlProperty CodeAndNameXmlAttribute facility;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "icds")
    List<Icd> icd;

    @JacksonXmlProperty ValueOnlyXmlAttribute id;
    @JacksonXmlProperty ValueOnlyXmlAttribute location;
    @JacksonXmlProperty ValueOnlyXmlAttribute patientClass;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "providers")
    List<Provider> provider;

    @JacksonXmlProperty Reason reason;
    @JacksonXmlProperty ValueOnlyXmlAttribute service;
    @JacksonXmlProperty CodeAndNameXmlAttribute serviceCategory;
    @JacksonXmlProperty CodeAndNameXmlAttribute stopCode;
    @JacksonXmlProperty CodeAndNameXmlAttribute type;
    @JacksonXmlProperty ValueOnlyXmlAttribute visitString;
    @JacksonXmlProperty ValueOnlyXmlAttribute admission;
    @JacksonXmlProperty ValueOnlyXmlAttribute arrivalDateTime;
    @JacksonXmlProperty ValueOnlyXmlAttribute departureDateTime;
    @JacksonXmlProperty ValueOnlyXmlAttribute ptf;
    @JacksonXmlProperty ValueOnlyXmlAttribute roomBed;
    @JacksonXmlProperty ValueOnlyXmlAttribute specialty;

    /** Check if a problem result is empty (e.g. all fields are null). */
    @JsonIgnore
    public boolean isNotEmpty() {
      return !equals(EMPTY);
    }
  }

  /** Visit document. */
  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Document {
    @JacksonXmlProperty(isAttribute = true)
    String id;

    @JacksonXmlProperty(isAttribute = true)
    String localTitle;

    @JacksonXmlProperty(isAttribute = true)
    String nationalTitle;

    @JacksonXmlProperty(isAttribute = true)
    String vuid;

    @JacksonXmlProperty(isAttribute = true)
    String content;
  }

  /** Visit icd. */
  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Icd {
    @JacksonXmlProperty(isAttribute = true)
    String code;

    @JacksonXmlProperty(isAttribute = true)
    String name;

    @JacksonXmlProperty(isAttribute = true)
    String system;

    @JacksonXmlProperty(isAttribute = true)
    String narrative;

    @JacksonXmlProperty(isAttribute = true)
    String ranking;
  }

  /** Visit provider. */
  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Provider {
    @JacksonXmlProperty(isAttribute = true)
    String code;

    @JacksonXmlProperty(isAttribute = true)
    String name;

    @JacksonXmlProperty(isAttribute = true)
    String role;

    @JacksonXmlProperty(isAttribute = true)
    String primary;

    @JacksonXmlProperty(isAttribute = true)
    String officePhone;

    @JacksonXmlProperty(isAttribute = true)
    String analogPager;

    @JacksonXmlProperty(isAttribute = true)
    String fax;

    @JacksonXmlProperty(isAttribute = true)
    String email;

    @JacksonXmlProperty(isAttribute = true)
    String taxonomyCode;

    @JacksonXmlProperty(isAttribute = true)
    String providerType;

    @JacksonXmlProperty(isAttribute = true)
    String classification;

    @JacksonXmlProperty(isAttribute = true)
    String specialization;

    @JacksonXmlProperty(isAttribute = true)
    String service;
  }

  /** Visit reason. */
  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Reason {
    @JacksonXmlProperty(isAttribute = true)
    String code;

    @JacksonXmlProperty(isAttribute = true)
    String name;

    @JacksonXmlProperty(isAttribute = true)
    String system;

    @JacksonXmlProperty(isAttribute = true)
    String narrative;
  }
}
