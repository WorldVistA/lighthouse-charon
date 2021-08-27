package gov.va.api.lighthouse.charon.api;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.AssertTrue;

public interface IsPrincipal {

  String accessCode();

  String applicationProxyUser();

  @SuppressWarnings("unused")
  @JsonIgnore
  @AssertTrue(message = "Invalid property combination.")
  default boolean isValid() {
    return type() != LoginType.INVALID;
  }

  /**
   * Determine the type of principal information. INVALID will be returned if enough information is
   * not available to satisfy any login type.
   */
  @JsonIgnore
  default LoginType type() {
    if (isNotBlank(accessCode())
        && isNotBlank(verifyCode())
        && isNotBlank(applicationProxyUser())) {
      return LoginType.APPLICATION_PROXY_USER;
    }
    if (isNotBlank(accessCode()) && isNotBlank(verifyCode())) {
      return LoginType.AV_CODES;
    }
    return LoginType.INVALID;
  }

  String verifyCode();

  /** All known LoginTypes. */
  enum LoginType {
    AV_CODES,
    APPLICATION_PROXY_USER,
    INVALID
  }
}
