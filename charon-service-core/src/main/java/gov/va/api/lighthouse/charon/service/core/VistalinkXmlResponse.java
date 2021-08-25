package gov.va.api.lighthouse.charon.service.core;

import gov.va.med.vistalink.rpc.RpcResponse;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

/** Model class for vistalink responses. */
@XmlRootElement(name = "VistaLink")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Accessors(fluent = false)
public class VistalinkXmlResponse {
  private static final JAXBContext JAXB_CONTEXT = createJaxbContext();

  @XmlElement(name = "Response")
  Payload response;

  @SneakyThrows
  public static JAXBContext createJaxbContext() {
    return JAXBContext.newInstance(VistalinkXmlResponse.class);
  }

  /** Create a response object by parsing the raw data. */
  @SneakyThrows
  public static VistalinkXmlResponse parse(RpcResponse rpcResponse) {
    Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
    return (VistalinkXmlResponse)
        unmarshaller.unmarshal(new StringReader(rpcResponse.getRawResponse()));
  }

  /** Payload. */
  @XmlType
  @XmlAccessorType(XmlAccessType.FIELD)
  @Data
  @Accessors(fluent = false)
  public static class Payload {
    @XmlAttribute private String type;

    @XmlValue private String value;
  }
}
