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

/** Container class for vista Appointment models. */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Appointments {
  @JacksonXmlProperty(isAttribute = true)
  Integer total;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "appointment")
  List<Appointment> appointmentResults;

  /** Lazy Initializer. */
  public List<Appointments.Appointment> appointmentResults() {
    if (appointmentResults == null) {
      appointmentResults = new ArrayList<>();
    }
    return appointmentResults;
  }

  /** Appointment model. */
  @Data
  @Builder
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Appointment {
    private static final Appointments.Appointment EMPTY = new Appointments.Appointment();

    @JacksonXmlProperty ValueOnlyXmlAttribute apptStatus;

    @JacksonXmlProperty CodeAndNameXmlAttribute clinicStop;

    @JacksonXmlProperty ValueOnlyXmlAttribute dateTime;

    @JacksonXmlProperty CodeAndNameXmlAttribute facility;

    @JacksonXmlProperty ValueOnlyXmlAttribute id;

    @JacksonXmlProperty ValueOnlyXmlAttribute location;

    @JacksonXmlProperty ValueOnlyXmlAttribute patientClass;

    @JacksonXmlProperty CodeAndNameXmlAttribute provider;

    @JacksonXmlProperty ValueOnlyXmlAttribute service;

    @JacksonXmlProperty CodeAndNameXmlAttribute serviceCategory;

    @JacksonXmlProperty CodeAndNameXmlAttribute type;

    @JacksonXmlProperty ValueOnlyXmlAttribute visitString;

    /** Check if an Appointment result is empty (e.g. all fields are null). */
    @JsonIgnore
    public boolean isNotEmpty() {
      return !equals(EMPTY);
    }
  }
}
