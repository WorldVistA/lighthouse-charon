package gov.va.api.lighthouse.charon.service.core;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.IsPrincipal;

public class VistalinkSessionFactory {

  /**
   * Create a session for A/V codes, application proxy user, or throw an Illegal Argument Exception.
   */
  public static VistalinkSession create(
      IsPrincipal principal, ConnectionDetails connectionDetails) {
    // noinspection EnhancedSwitchMigration
    switch (principal.type()) {
      case AV_CODES:
        return StandardUserVistalinkSession.builder()
            .connectionDetails(connectionDetails)
            .accessCode(principal.accessCode())
            .verifyCode(principal.verifyCode())
            .build();
      case APPLICATION_PROXY_USER:
        return ApplicationProxyUserVistalinkSession.builder()
            .connectionDetails(connectionDetails)
            .accessCode(principal.accessCode())
            .verifyCode(principal.verifyCode())
            .applicationProxyUser(principal.applicationProxyUser())
            .build();
      default:
        throw new IllegalArgumentException("Unsupported RPC principal type: " + principal.type());
    }
  }
}
