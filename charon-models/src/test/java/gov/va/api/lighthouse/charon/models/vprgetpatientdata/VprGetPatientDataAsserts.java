package gov.va.api.lighthouse.charon.models.vprgetpatientdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;

public class VprGetPatientDataAsserts {

  static final ObjectMapper XML_MAPPER =
      new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @SneakyThrows
  static <T> void assertDeserializedEquals(String xmlResponseResource, T expected) {
    var xml =
        new String(
            VprGetPatientDataAsserts.class.getResourceAsStream(xmlResponseResource).readAllBytes(),
            StandardCharsets.UTF_8);
    var actual = XML_MAPPER.readValue(xml, expected.getClass());
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
