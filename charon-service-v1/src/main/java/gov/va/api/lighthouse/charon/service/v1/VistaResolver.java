package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;

interface VistaResolver {
  ConnectionDetails resolve(String vista);
}
