package gov.va.api.lighthouse.charon.tests;

import static gov.va.api.lighthouse.charon.tests.TestOptions.assumeEnabled;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import gov.va.api.lighthouse.charon.api.ConnectionDetailsParser;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.controller.LocalDateMacro;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessorFactory;
import gov.va.api.lighthouse.charon.service.v1.MacroEnabledRpcInvokerFactoryV1;
import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FileRpcRequestTest {

  private final ObjectMapper mapper = JacksonConfig.createMapper();

  @Test
  @SneakyThrows
  void invokeRequest() {
    assumeEnabled("test.file-rpc-request");
    var invokerFactory =
        new MacroEnabledRpcInvokerFactoryV1(
            new MacroProcessorFactory(List.of(new LocalDateMacro())));
    var request = loadRequestV1();

    var connectionDetails =
        ConnectionDetailsParser.parse(request.vista())
            .orElseThrow(() -> new ConnectionSpecRequired(request.vista()));
    var result = invokerFactory.create(request, connectionDetails).invoke();
    try {
      var tree = mapper.readTree(result.response());
      log.info(
          "RESPONSE START\n{}\nRESPONSE END",
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
    } catch (Exception e) {
      log.info(
          "RESPONSE START\n{}\nRESPONSE END",
          mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }
  }

  @SneakyThrows
  RpcRequestV1 loadRequestV1() {
    String fileName = TestOptions.valueOf("test.file-rpc-request.file", "/request.json");
    return mapper.readValue(new File(fileName), RpcRequestV1.class);
  }

  static class ConnectionSpecRequired extends RuntimeException {
    ConnectionSpecRequired(String got) {
      super("Expected hostname:port:divisionIen:timezone, got: " + got);
    }
  }
}
