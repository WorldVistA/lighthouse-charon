package gov.va.api.lighthouse.charon.service.controller;

import static java.util.stream.Collectors.toList;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.RpcVistaTargets;
import gov.va.api.lighthouse.charon.api.VistalinkProperties;
import gov.va.api.lighthouse.charon.service.config.VistalinkPropertiesConfig;
import gov.va.api.lighthouse.charon.service.controller.VistaLinkExceptions.UnknownVista;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** Provides rules for getting connection details for a set of vista targets. */
@Slf4j
@Builder
public class NameResolution {
  @Getter private final VistalinkProperties properties;

  private final Function<RpcVistaTargets, Collection<String>> additionalCandidates;

  /** A function that adds nothing. */
  public static Function<RpcVistaTargets, Collection<String>> noAdditionalCandidates() {
    return t -> List.of();
  }

  /** Throw a UnknownVista exception if any of the candidate names are unknown. */
  private void checkKnownNames(List<String> candidateNames) {
    if (candidateNames == null || candidateNames.isEmpty()) {
      return;
    }
    var knownVistas = properties.names();
    var unknownVistas =
        candidateNames.stream()
            .filter(include -> !knownVistas.contains(include))
            .collect(Collectors.toList());
    if (!unknownVistas.isEmpty()) {
      throw new UnknownVista(unknownVistas.toString());
    }
  }

  /**
   * Resolve the targets by applying the following rules.
   * <li>Include values are checked to see if they are names or specifications. Names are validated
   *     to be known. Specifications are parsed.
   * <li>Exclude values are validated to be known.
   * <li>Included names and additional candidate names are combined as a set of overall candidates.
   * <li>Any excluded names are removed the list.
   * <li>Any included full specifications are added.
   */
  public List<ConnectionDetails> resolve(RpcVistaTargets rpcVistaTargets) {
    List<ConnectionDetails> explicitlyDefined = new ArrayList<>();
    Set<String> vistas = new HashSet<>();
    if (!rpcVistaTargets.include().isEmpty()) {
      List<String> namedVistas = new ArrayList<>(rpcVistaTargets.include().size());
      for (String included : rpcVistaTargets.include()) {
        var details = VistalinkPropertiesConfig.parse(included);
        if (details.isEmpty()) {
          namedVistas.add(included);
        } else {
          explicitlyDefined.add(details.get());
        }
      }
      checkKnownNames(namedVistas);
      vistas.addAll(namedVistas);
    }
    vistas.addAll(additionalCandidates.apply(rpcVistaTargets));
    if (!rpcVistaTargets.exclude().isEmpty()) {
      checkKnownNames(rpcVistaTargets.exclude());
      vistas.removeAll(rpcVistaTargets.exclude());
    }
    var knownVistas = properties.names();
    vistas.removeIf(s -> !knownVistas.contains(s));
    log.info("Known Vistas: {}", knownVistas);
    return Stream.concat(
            explicitlyDefined.stream(),
            properties().vistas().stream().filter(c -> vistas.contains(c.name())))
        .collect(toList());
  }
}
