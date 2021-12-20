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

/** Container for vista problems models. */
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Problems {

  @JacksonXmlProperty(isAttribute = true)
  Integer total;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "problem")
  List<Problem> problemResults;

  /** Lazy Initializer. */
  public List<Problem> problemResults() {
    if (problemResults == null) {
      problemResults = new ArrayList<>();
    }
    return problemResults;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Problem {
    private static final Problem EMPTY = new Problem();

    @JacksonXmlProperty CodeAndNameXmlAttribute acuity;
    @JacksonXmlProperty ValueOnlyXmlAttribute codingSystem;
    @JacksonXmlProperty Comment comment;
    @JacksonXmlProperty ValueOnlyXmlAttribute entered;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    List<ValueOnlyXmlAttribute> exposure;

    @JacksonXmlProperty CodeAndNameXmlAttribute facility;
    @JacksonXmlProperty ValueOnlyXmlAttribute icd;
    @JacksonXmlProperty ValueOnlyXmlAttribute icdd;
    @JacksonXmlProperty ValueOnlyXmlAttribute id;
    @JacksonXmlProperty ValueOnlyXmlAttribute location;
    @JacksonXmlProperty ValueOnlyXmlAttribute name;
    @JacksonXmlProperty ValueOnlyXmlAttribute onset;
    @JacksonXmlProperty CodeAndNameXmlAttribute provider;
    @JacksonXmlProperty ValueOnlyXmlAttribute removed;
    @JacksonXmlProperty ValueOnlyXmlAttribute resolved;
    @JacksonXmlProperty ValueOnlyXmlAttribute sc;
    @JacksonXmlProperty ValueOnlyXmlAttribute sctc;
    @JacksonXmlProperty ValueOnlyXmlAttribute sctd;
    @JacksonXmlProperty ValueOnlyXmlAttribute sctt;
    @JacksonXmlProperty ValueOnlyXmlAttribute service;
    @JacksonXmlProperty CodeAndNameXmlAttribute status;
    @JacksonXmlProperty ValueOnlyXmlAttribute unverified;
    @JacksonXmlProperty ValueOnlyXmlAttribute updated;

    /** Check if a problem result is empty (e.g. all fields are null). */
    @JsonIgnore
    public boolean isNotEmpty() {
      return !equals(EMPTY);
    }
  }

  /** Problem comment. */
  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Comment {
    @JacksonXmlProperty(isAttribute = true)
    String id;

    @JacksonXmlProperty(isAttribute = true)
    String enteredBy;

    @JacksonXmlProperty(isAttribute = true)
    String entered;

    @JacksonXmlProperty(isAttribute = true)
    String commentText;
  }
}
