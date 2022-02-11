package gov.va.api.lighthouse.charon.api.v1;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;

/** Provides helper methods for searching all know RpcPrincipals. */
@Builder
@AllArgsConstructor(staticName = "of")
public class RpcPrincipalLookupV1 {
  RpcPrincipalsV1 rpcPrincipalsV1;

  /** Find all entries. */
  public Map<String, RpcPrincipalV1> findAllEntries() {
    var entries = rpcPrincipalsV1.entries();
    return mapPrincipalEntriesBySite(entries);
  }

  /** Find all principals for a given RPC name organized by site ID. */
  public Map<String, RpcPrincipalV1> findByName(String rpcName) {
    var entries = findEntriesByName(rpcName);
    return mapPrincipalEntriesBySite(entries);
  }

  /** Return the principal for a given RPC name at a given vista site. */
  public Optional<RpcPrincipalV1> findByNameAndSite(String rpcName, String site) {
    if (isBlank(rpcName) || isBlank(site)) {
      return Optional.empty();
    }
    return Optional.ofNullable(findByName(rpcName).get(site));
  }

  /** Find all principals with the matching tag. */
  public Map<String, RpcPrincipalV1> findByTag(String tag) {
    var entries = findEntriesByTag(tag);
    return mapPrincipalEntriesBySite(entries);
  }

  private List<RpcPrincipalsV1.PrincipalEntry> findEntriesByName(String rpcName) {
    return rpcPrincipalsV1.entries().stream()
        .filter(principalEntry -> principalEntry.rpcNames().contains(rpcName))
        .collect(toList());
  }

  private List<RpcPrincipalsV1.PrincipalEntry> findEntriesByTag(String tag) {
    return rpcPrincipalsV1.entries().stream()
        .filter(principalEntry -> principalEntry.tags().contains(tag))
        .collect(toList());
  }

  private Map<String, RpcPrincipalV1> mapPrincipalEntriesBySite(
      List<RpcPrincipalsV1.PrincipalEntry> entries) {
    if (entries.isEmpty()) {
      return Map.of();
    }
    Map<String, RpcPrincipalV1> principals = new HashMap<>();
    for (RpcPrincipalsV1.PrincipalEntry e : entries) {
      String apu = e.applicationProxyUser();
      for (RpcPrincipalsV1.Codes c : e.codes()) {
        for (String s : c.sites()) {
          principals.put(
              s,
              RpcPrincipalV1.builder()
                  .applicationProxyUser(apu)
                  .verifyCode(c.verifyCode())
                  .accessCode(c.accessCode())
                  .build());
        }
      }
    }
    return principals;
  }
}
