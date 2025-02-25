package gov.va.api.lighthouse.charon.service.controller;

import static gov.va.api.health.autoconfig.logging.LogSanitizer.sanitize;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import gov.va.api.health.autoconfig.logging.Redact;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalLookupV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.models.lhscheckoptionaccess.LhsCheckOptionAccess;
import gov.va.api.lighthouse.charon.models.lhscheckoptionaccess.LhsCheckOptionAccess.Request;
import gov.va.api.lighthouse.charon.service.config.AuthorizationId;
import gov.va.api.lighthouse.charon.service.core.EncryptedLogging;
import gov.va.api.lighthouse.charon.service.v1.RpcControllerV1;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authorization-status endpoint. Makes a request to the LHSCheckOptionAccess Rpc to
 * determine if the request's access credentials are valid.
 */
@Validated
@RestController()
@RequestMapping(
    path = "/authorization-status",
    produces = {"application/json"})
@Slf4j
public class AuthorizationStatusController {

  private final AlternateAuthorizationStatusIds alternateIds;

  private final EncryptedLogging encryptedLogging;

  private final RpcPrincipalLookupV1 rpcPrincipalLookup;

  private final String defaultMenuOption;

  private final RpcControllerV1 controller;

  /** AuthorizationStatusController constructor with field validation. */
  public AuthorizationStatusController(
      @Autowired RpcControllerV1 controller,
      @Autowired AlternateAuthorizationStatusIds alternateIds,
      @Autowired EncryptedLogging encryptedLogging,
      @Autowired RpcPrincipalLookupV1 rpcPrincipalLookup,
      @org.springframework.beans.factory.annotation.Value(
              "${clinical-authorization-status.default-menu-option}")
          String defaultMenuOption) {
    this.controller = controller;
    this.alternateIds = alternateIds;
    this.encryptedLogging = encryptedLogging;
    this.rpcPrincipalLookup = rpcPrincipalLookup;
    if (isBlank(defaultMenuOption)) {
      throw new IllegalArgumentException(
          "${clinical-authorization-status.default-menu-option} is not set or is empty.");
    }
    this.defaultMenuOption = defaultMenuOption;
  }

  /** Test a users clinical authorization status. Uses the LHS CHECK OPTION ACCESS VPC. */
  @GetMapping(
      value = {"/clinical"},
      params = {"site", "duz"})
  public ResponseEntity<ClinicalAuthorizationResponse> clinicalAuthorization(
      @NotBlank @RequestParam(name = "site") String unsafeSite,
      @Redact @NotBlank @RequestParam(name = "duz") String unsafeDuz,
      @RequestParam(name = "menu-option", required = false) String menuOption) {

    if (isBlank(menuOption)) {
      menuOption = defaultMenuOption;
    }
    AuthorizationId specifiedAuthorizationId =
        AuthorizationId.builder().duz(unsafeDuz).site(unsafeSite).build();
    AuthorizationId usableAuthorizationId = alternateIds.toPrivateId(specifiedAuthorizationId);
    log.info(
        "Checking {}",
        encryptedLogging.encrypt(
            String.format(
                "Option %s, requested %s, using %s",
                menuOption, specifiedAuthorizationId, usableAuthorizationId)));
    var principal =
        rpcPrincipalLookup.findByNameAndSite(
            LhsCheckOptionAccess.RPC_NAME, usableAuthorizationId.site());
    if (principal.isEmpty()) {
      return responseOf(
          "No credentials for site.",
          sanitize(
              String.format(
                  "Site: %s. Alternate site: %s.", unsafeSite, usableAuthorizationId.site())),
          400);
    }

    var resultV1 =
        controller.invoke(
            RpcRequestV1.builder()
                .rpc(
                    Request.builder()
                        .duz(usableAuthorizationId.duz())
                        .menuOption(menuOption)
                        .build()
                        .asDetails())
                .vista(usableAuthorizationId.site())
                .principal(principal.get())
                .build());
    LhsCheckOptionAccess.Response typeSafeResult =
        LhsCheckOptionAccess.Response.builder()
            .resultsByStation(Map.of(resultV1.vista(), resultV1.response()))
            .build();
    return parseLhsCheckOptionAccessResponse(
        typeSafeResult.resultsByStation(), usableAuthorizationId.site());
  }

  ResponseEntity<ClinicalAuthorizationResponse> parseLhsCheckOptionAccessResponse(
      Map<String, String> results, String site) {
    if (results.size() != 1) {
      return responseOf(
          "Only expecting one result from site: " + site,
          results.values().stream().sorted().collect(Collectors.joining(", ")),
          500);
    }
    if (!results.containsKey(site)) {
      return responseOf(
          "Response missing for site: " + site, String.join(", ", results.values()), 500);
    }
    if (isBlank(results.get(site))) {
      return responseOf("Blank response from vista", null, 500);
    }
    String authorizationString = results.get(site).split("[\\^]", -1)[0];
    int authorizationStatusPiece;
    try {
      authorizationStatusPiece = Integer.parseInt(authorizationString);
    } catch (NumberFormatException e) {
      return responseOf("Cannot parse authorization response.", authorizationString, 500);
    }
    /*
     *  -1: no such user in the New Person File.
     *  -2: User terminated or has no access code.
     *  -3: no such option in the Option File.
     *  0: no access found in any menu tree the user owns.
     *  Positive cases are access allowed.
     */
    if (authorizationStatusPiece > 0) {
      return responseOf("ok", authorizationString, 200);
    }
    if (authorizationStatusPiece == -1) {
      return responseOf("unauthorized", authorizationString, 401);
    }
    return responseOf("forbidden", authorizationString, 403);
  }

  private ResponseEntity<ClinicalAuthorizationResponse> responseOf(
      String status, String value, int httpCode) {
    log.info("Responding [{}] {} ({})", httpCode, status, value);
    return ClinicalAuthorizationResponse.builder()
        .status(status)
        .value(value)
        .build()
        .response(httpCode);
  }

  /** Response object for the clinical authorization endpoint. */
  @Value
  @Builder
  @JsonAutoDetect(fieldVisibility = Visibility.ANY)
  public static class ClinicalAuthorizationResponse {
    String status;

    @Schema(
        description =
            "Menu option access value: "
                + "-1, no such user in the New Person File.\n"
                + "-2: User terminated or has no access code.\n"
                + "-3: no such option in the Option File.\n"
                + " 0: no access found in any menu tree the user owns.\n"
                + " Positive cases are access allowed. ")
    String value;

    ResponseEntity<ClinicalAuthorizationResponse> response(int httpCode) {
      return ResponseEntity.status(httpCode).body(this);
    }
  }
}
