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

/** Container class for vista Med models. */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Meds {
  @JacksonXmlProperty(isAttribute = true)
  Integer total;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "med")
  List<Med> medResults;

  /** Lazy Initializer. */
  public List<Med> medResults() {
    if (medResults == null) {
      medResults = new ArrayList<>();
    }
    return medResults;
  }

  /** Med model. */
  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Med {
    static final Med EMPTY = new Med();

    @JacksonXmlProperty Provider currentProvider;

    @JacksonXmlProperty ValueOnlyXmlAttribute daysSupply;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "doses")
    List<Dose> dose;

    @JacksonXmlProperty ValueOnlyXmlAttribute expires;

    @JacksonXmlProperty CodeAndNameXmlAttribute facility;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "fills")
    List<Fill> fill;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillCost;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillsAllowed;

    @JacksonXmlProperty ValueOnlyXmlAttribute fillsRemaining;

    @JacksonXmlProperty ValueOnlyXmlAttribute form;

    @JacksonXmlProperty ValueOnlyXmlAttribute id;

    @JacksonXmlProperty(localName = "IMO")
    ValueOnlyXmlAttribute imo;

    @JacksonXmlProperty ValueOnlyXmlAttribute ivLimit;

    @JacksonXmlProperty ValueOnlyXmlAttribute lastFilled;

    @JacksonXmlProperty CodeAndNameXmlAttribute location;

    @JacksonXmlProperty(localName = "medID")
    ValueOnlyXmlAttribute medId;

    @JacksonXmlProperty ValueOnlyXmlAttribute name;

    @JacksonXmlProperty ValueOnlyXmlAttribute ordered;

    @JacksonXmlProperty(localName = "orderID")
    ValueOnlyXmlAttribute orderId;

    @JacksonXmlProperty Provider orderingProvider;

    @JacksonXmlProperty ValueOnlyXmlAttribute parent;

    @JacksonXmlProperty CodeAndNameXmlAttribute pharmacist;

    @JacksonXmlProperty ValueOnlyXmlAttribute prescription;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(localName = "products")
    List<Product> product;

    @JacksonXmlProperty ValueOnlyXmlAttribute ptInstructions;

    @JacksonXmlProperty ValueOnlyXmlAttribute quantity;

    @JacksonXmlProperty ValueOnlyXmlAttribute rate;

    @JacksonXmlProperty ValueOnlyXmlAttribute routing;

    @JacksonXmlProperty String sig;

    @JacksonXmlProperty ValueOnlyXmlAttribute start;

    @JacksonXmlProperty ValueOnlyXmlAttribute status;

    @JacksonXmlProperty ValueOnlyXmlAttribute stop;

    @JacksonXmlProperty ValueOnlyXmlAttribute supply;

    @JacksonXmlProperty ValueOnlyXmlAttribute type;

    @JacksonXmlProperty ValueOnlyXmlAttribute vaStatus;

    @JacksonXmlProperty ValueOnlyXmlAttribute vaType;

    @JsonIgnore
    public boolean isNotEmpty() {
      return !equals(EMPTY);
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Product {
      @JacksonXmlProperty(isAttribute = true)
      String code;

      @JacksonXmlProperty(isAttribute = true)
      String name;

      @JacksonXmlProperty(isAttribute = true)
      String role;

      @JacksonXmlProperty(isAttribute = true)
      String concentration;

      @JacksonXmlProperty(isAttribute = true)
      String order;

      @JacksonXmlProperty CodeAndNameXmlAttribute ordItem;

      @JacksonXmlProperty(localName = "class")
      ProductDetail clazz;

      @JacksonXmlProperty ProductDetail vaGeneric;

      @JacksonXmlProperty ProductDetail vaProduct;

      @AllArgsConstructor
      @Builder
      @Data
      @JsonInclude(JsonInclude.Include.NON_EMPTY)
      @NoArgsConstructor(access = AccessLevel.PRIVATE)
      static class ProductDetail {
        @JacksonXmlProperty(isAttribute = true)
        String code;

        @JacksonXmlProperty(isAttribute = true)
        String name;

        @JacksonXmlProperty(isAttribute = true)
        String vuid;
      }
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Fill {
      @JacksonXmlProperty(isAttribute = true)
      String fillDate;

      @JacksonXmlProperty(isAttribute = true)
      String fillRouting;

      @JacksonXmlProperty(isAttribute = true)
      String releaseDate;

      @JacksonXmlProperty(isAttribute = true)
      String fillQuantity;

      @JacksonXmlProperty(isAttribute = true)
      String fillDaysSupply;
    }

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Provider {
      @JacksonXmlProperty(isAttribute = true)
      String code;

      @JacksonXmlProperty(isAttribute = true)
      String name;

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

    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static class Dose {
      @JacksonXmlProperty(isAttribute = true)
      String dose;

      @JacksonXmlProperty(isAttribute = true)
      String conjunction;

      @JacksonXmlProperty(isAttribute = true)
      String doseStart;

      @JacksonXmlProperty(isAttribute = true)
      String doseStop;

      @JacksonXmlProperty(isAttribute = true)
      String duration;

      @JacksonXmlProperty(isAttribute = true)
      String noun;

      @JacksonXmlProperty(isAttribute = true)
      String order;

      @JacksonXmlProperty(isAttribute = true)
      String route;

      @JacksonXmlProperty(isAttribute = true)
      String schedule;

      @JacksonXmlProperty(isAttribute = true)
      String units;

      @JacksonXmlProperty(isAttribute = true)
      String unitsPerDose;
    }
  }
}
