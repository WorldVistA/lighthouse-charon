package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.health.autoconfig.logging.Redact;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.core.EncryptedLogging;
import java.time.Instant;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RpcControllerV1 {

  private final VistalinkProperties vistalinkProperties;
  private final EncryptedLogging encryptedLogging;

  @GetMapping("/connections")
  public VistalinkProperties connections() {
    return vistalinkProperties;
  }

  /** Process the RPC request. */
  @PostMapping(consumes = {"application/json"})
  public RpcInvocationResultV1 invoke(@Redact @RequestBody @Valid RpcRequestV1 request) {
    log.info("Request: {}", encryptedLogging.encrypt(request.toString()));
    return RpcInvocationResultV1.builder().response("hi " + Instant.now()).build();
  }
}
