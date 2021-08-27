package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(
    path = "/v1/rpc/connections",
    produces = {"application/json"})
@AllArgsConstructor
public class ConnectionControllerV1 {

  private final VistalinkProperties vistalinkProperties;

  @GetMapping
  public VistalinkProperties connections() {
    return vistalinkProperties;
  }
}
