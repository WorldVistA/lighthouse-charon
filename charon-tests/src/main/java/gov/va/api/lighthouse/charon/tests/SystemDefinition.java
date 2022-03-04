package gov.va.api.lighthouse.charon.tests;

import gov.va.api.health.sentinel.ServiceDefinition;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalV1;
import gov.va.api.lighthouse.charon.tests.SystemDefinitions.SiteDuzPair;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

/** Defines all data needed for making test requests in an environment. */
@Value
@Builder
public class SystemDefinition {
  @NotNull ServiceDefinition charon;
  @NotNull TestRpcs testRpcs;
  Optional<String> clientKey;
  boolean isVistaAvailable;
  @NotNull RpcPrincipalV1 avCodePrincipal;
  @NotNull SiteDuzPair authorizedClinicalUser;
  @NotNull String vistaSite;
}
