package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.health.autoconfig.logging.Redact;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.core.EncryptedLogging;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(
    path = "/v1/rpc",
    produces = {"application/json"})
@Slf4j
@AllArgsConstructor
public class RpcControllerV1 {

  private final VistaResolver vistaResolver;
  private final EncryptedLogging encryptedLogging;
  private final RpcInvokerFactoryV1 invokerFactory;

  /** Process the RPC request. */
  @PostMapping(consumes = {"application/json"})
  public RpcInvocationResultV1 invoke(@Redact @RequestBody @Valid RpcRequestV1 request) {
    log.info("Request: {}", encryptedLogging.encrypt(request.toString()));
    var connectionDetails = vistaResolver.resolve(request.vista());
    try (var invoker = invokerFactory.create(request, connectionDetails)) {
      return invoker.invoke();
    }
  }
}
