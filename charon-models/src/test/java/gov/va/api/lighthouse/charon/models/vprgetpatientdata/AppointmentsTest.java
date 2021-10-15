package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.models.CodeAndNameXmlAttribute;
import gov.va.api.lighthouse.charon.models.ValueOnlyXmlAttribute;
import io.micrometer.core.instrument.util.IOUtils;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AppointmentsTest {
  AppointmentsSamples appointmentsSamples = AppointmentsSamples.create();

  @Test
  public static Stream<Arguments> isNotEmpty() {
    return Stream.of(
        Arguments.of(Appointments.Appointment.builder().build(), false),
        Arguments.of(
            Appointments.Appointment.builder().apptStatus(ValueOnlyXmlAttribute.of("a")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder()
                .clinicStop(CodeAndNameXmlAttribute.of("a", "b"))
                .build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().dateTime(ValueOnlyXmlAttribute.of("a")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder()
                .facility(CodeAndNameXmlAttribute.of("a", "b"))
                .build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().id(ValueOnlyXmlAttribute.of("a")).build(), true),
        Arguments.of(
            Appointments.Appointment.builder().location(ValueOnlyXmlAttribute.of("a")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().patientClass(ValueOnlyXmlAttribute.of("a")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder()
                .provider(CodeAndNameXmlAttribute.of("a", "b"))
                .build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().service(ValueOnlyXmlAttribute.of("a")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder()
                .serviceCategory(CodeAndNameXmlAttribute.of("a", "b"))
                .build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().type(CodeAndNameXmlAttribute.of("a", "b")).build(),
            true),
        Arguments.of(
            Appointments.Appointment.builder().visitString(ValueOnlyXmlAttribute.of("a")).build(),
            true));
  }

  @Test
  void appointmentsStream() {
    assertThat(VprGetPatientData.Response.Results.builder().build().appointmentStream()).isEmpty();
    var sample =
        VprGetPatientDataSamples.Response.create()
            .resultsByStation(appointmentsSamples.appointments())
            .get("673")
            .appointmentStream()
            .collect(Collectors.toList());
    assertThat(sample).isEqualTo(appointmentsSamples.appointmentResults());
  }

  @SneakyThrows
  @Test
  void deserialize() {
    var xml = IOUtils.toString(getClass().getResourceAsStream("/SampleAppointmentsResult.xml"));
    var result = RpcInvocationResult.builder().vista("673").response(xml).build();
    assertThat(VprGetPatientData.create().fromResults(List.of(result)))
        .isEqualTo(
            VprGetPatientDataSamples.Response.create()
                .responseFor(appointmentsSamples.appointments()));
  }

  @ParameterizedTest
  @MethodSource
  void isNotEmpty(Appointments.Appointment appointment, boolean expected) {
    assertThat(appointment.isNotEmpty()).isEqualTo(expected);
  }
}
