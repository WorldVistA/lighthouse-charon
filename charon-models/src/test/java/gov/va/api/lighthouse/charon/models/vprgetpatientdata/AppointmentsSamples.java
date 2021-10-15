package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "create")
public class AppointmentsSamples {
  public List<Appointments.Appointment> appointmentResults() {
    return List.of(
        Appointments.Appointment.builder()
            .apptStatus(ValueOnlyXmlAttribute.of("NO-SHOW"))
            .clinicStop(CodeAndNameXmlAttribute.of("301", "GENERAL INTERNAL MEDICINE"))
            .dateTime(ValueOnlyXmlAttribute.of("2931013.07"))
            .facility(CodeAndNameXmlAttribute.of("673", "TAMPA (JAH VAH)"))
            .id(ValueOnlyXmlAttribute.of("A;2931013.07;23"))
            .location(ValueOnlyXmlAttribute.of("GENERAL MEDICINE"))
            .patientClass(ValueOnlyXmlAttribute.of("AMB"))
            .provider(CodeAndNameXmlAttribute.of("1085", "MELDRUM,KEVIN"))
            .service(ValueOnlyXmlAttribute.of("MEDICINE"))
            .serviceCategory(CodeAndNameXmlAttribute.of("A", "AMBULATORY"))
            .type(CodeAndNameXmlAttribute.of("9", "REGULAR"))
            .visitString(ValueOnlyXmlAttribute.of("23;2931013.07;A"))
            .build());
  }

  public Appointments appointments() {
    return Appointments.builder().total(1).appointmentResults(appointmentResults()).build();
  }
}
