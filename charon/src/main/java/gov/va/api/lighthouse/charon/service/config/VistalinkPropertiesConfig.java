package gov.va.api.lighthouse.charon.service.config;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.ConnectionDetailsParser;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Load Vistalink Properties from file into a config for use. */
@Configuration
@NoArgsConstructor
@Slf4j
public class VistalinkPropertiesConfig {

  @Bean
  @SneakyThrows
  VistalinkProperties load(
      @Value("${vistalink.configuration:vistalink.properties}") String vistalinkProperties) {
    Properties properties = new Properties();
    try (var in = new FileInputStream(vistalinkProperties)) {
      properties.load(in);
    }
    List<ConnectionDetails> vistalinkDetails = parseProperties(properties);
    log.info("Loaded {} vista sites from {}", vistalinkDetails.size(), vistalinkProperties);
    return VistalinkProperties.builder().vistas(vistalinkDetails).build();
  }

  List<ConnectionDetails> parseProperties(Properties p) {
    return p.stringPropertyNames().stream()
        .map(name -> ConnectionDetailsParser.asConnectionDetails(name, p.getProperty(name)))
        .collect(Collectors.toList());
  }
}
