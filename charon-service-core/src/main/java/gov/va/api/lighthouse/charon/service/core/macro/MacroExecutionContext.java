package gov.va.api.lighthouse.charon.service.core.macro;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;

/** Interface that enables macro processing. */
public interface MacroExecutionContext {
  ConnectionDetails connectionDetails();
}
