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

public class Medications {
  @JacksonXmlProperty(isAttribute = true)
  Integer total;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "medication")
  List<Medication> medicationResults;

  /** Lazy Initializer. */
  public List<Medications.Medication> medicationResults() {
    if (medicationResults == null) {
      medicationResults = new ArrayList<>();
    }
    return medicationResults;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Medication {
    private static final Medication EMPTY = new Medication();

    @JacksonXmlProperty Provider currentProvider;

    @JacksonXmlProperty ValueOnlyXmlAttribute daysSupply;

    @JacksonXmlProperty Dose dose;

    @JacksonXmlProperty ValueOnlyXmlAttribute expires;

    @JacksonXmlProperty ValueOnlyXmlAttribute facility;

    @JacksonXmlProperty Fill fill;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillCost;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillsAllowed;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillsRemaining;

    @JacksonXmlProperty ValueOnlyXmlAttribute form;

    @JacksonXmlProperty ValueOnlyXmlAttribute id;

    @JacksonXmlProperty ValueOnlyXmlAttribute imo;

    @JacksonXmlProperty ValueOnlyXmlAttribute ivLimit;

    @JacksonXmlProperty ValueOnlyXmlAttribute lastFilled;

    @JacksonXmlProperty ValueOnlyXmlAttribute location;

    @JacksonXmlProperty ValueOnlyXmlAttribute name;

    @JacksonXmlProperty ValueOnlyXmlAttribute ordered;

    @JacksonXmlProperty ValueOnlyXmlAttribute orderId;

    @JacksonXmlProperty Provider orderingProvider;

    @JacksonXmlProperty ValueOnlyXmlAttribute parent;

    @JacksonXmlProperty Pharmacist pharmacist;

    @JacksonXmlProperty ValueOnlyXmlAttribute prescription;

    @JacksonXmlProperty Product product;

    @JacksonXmlProperty ValueOnlyXmlAttribute ptInstructions;

    @JacksonXmlProperty ValueOnlyXmlAttribute quantity;

    @JacksonXmlProperty ValueOnlyXmlAttribute rate;

    @JacksonXmlProperty CodeAndNameXmlAttribute routing;

    @JacksonXmlProperty ValueOnlyXmlAttribute sig;

    @JacksonXmlProperty ValueOnlyXmlAttribute start;

    @JacksonXmlProperty ValueOnlyXmlAttribute status;

    @JacksonXmlProperty ValueOnlyXmlAttribute stop;

    @JacksonXmlProperty ValueOnlyXmlAttribute supply;

    @JacksonXmlProperty ValueOnlyXmlAttribute type;

    @JacksonXmlProperty ValueOnlyXmlAttribute vaStatus;

    @JacksonXmlProperty CodeAndNameXmlAttribute vsType;

    @JacksonXmlProperty CodeAndNameXmlAttribute medId;

    @JsonIgnore
    public boolean isNotEmpty() {
      return !equals(EMPTY);
    }

    // VFQ -> charon -> docker

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Product {
      @JacksonXmlProperty ValueOnlyXmlAttribute code;

      @JacksonXmlProperty ValueOnlyXmlAttribute name;

      @JacksonXmlProperty ValueOnlyXmlAttribute role;

      @JacksonXmlProperty ValueOnlyXmlAttribute concentration;

      @JacksonXmlProperty ValueOnlyXmlAttribute order;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Fill {
      @JacksonXmlProperty ValueOnlyXmlAttribute fillDate;

      @JacksonXmlProperty ValueOnlyXmlAttribute fillRouting;

      @JacksonXmlProperty ValueOnlyXmlAttribute releaseDate;

      @JacksonXmlProperty ValueOnlyXmlAttribute fillQuantity;

      @JacksonXmlProperty ValueOnlyXmlAttribute fillDaysSupply;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Pharmacist {
      @JacksonXmlProperty ValueOnlyXmlAttribute code;

      @JacksonXmlProperty ValueOnlyXmlAttribute name;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Provider {
      @JacksonXmlProperty ValueOnlyXmlAttribute code;

      @JacksonXmlProperty ValueOnlyXmlAttribute name;

      @JacksonXmlProperty ValueOnlyXmlAttribute officePhone;

      @JacksonXmlProperty ValueOnlyXmlAttribute analogPager;

      @JacksonXmlProperty ValueOnlyXmlAttribute fax;

      @JacksonXmlProperty ValueOnlyXmlAttribute email;

      @JacksonXmlProperty ValueOnlyXmlAttribute taxonomyCode;

      @JacksonXmlProperty ValueOnlyXmlAttribute providerType;

      @JacksonXmlProperty ValueOnlyXmlAttribute classification;

      @JacksonXmlProperty ValueOnlyXmlAttribute specialization;

      @JacksonXmlProperty ValueOnlyXmlAttribute service;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class Dose {
      @JacksonXmlProperty ValueOnlyXmlAttribute dose;

      @JacksonXmlProperty CodeAndNameXmlAttribute conjunction;

      @JacksonXmlProperty ValueOnlyXmlAttribute doseStart;

      @JacksonXmlProperty ValueOnlyXmlAttribute doseStop;

      @JacksonXmlProperty ValueOnlyXmlAttribute duration;

      @JacksonXmlProperty ValueOnlyXmlAttribute noun;

      @JacksonXmlProperty ValueOnlyXmlAttribute order;

      @JacksonXmlProperty ValueOnlyXmlAttribute route;

      @JacksonXmlProperty ValueOnlyXmlAttribute schedule;

      @JacksonXmlProperty ValueOnlyXmlAttribute units;

      @JacksonXmlProperty ValueOnlyXmlAttribute unitsPerDose;
    }
  }
}
