package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.ConnectionDetailsParser;
import gov.va.api.lighthouse.charon.api.UnknownVista;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class ConfigurableVistaResolver implements VistaResolver {

  private final VistalinkProperties vistalinkProperties;

  @Override
  public ConnectionDetails resolve(String vistaOrSpec) {
    var maybeSpec = ConnectionDetailsParser.parse(vistaOrSpec);
    if (maybeSpec.isPresent()) {
      return maybeSpec.get();
    }
    maybeSpec =
        vistalinkProperties.vistas().stream()
            .filter(deets -> deets.name().equals(vistaOrSpec))
            .findFirst();
    return maybeSpec.orElseThrow(() -> new UnknownVista(vistaOrSpec));
  }
}
