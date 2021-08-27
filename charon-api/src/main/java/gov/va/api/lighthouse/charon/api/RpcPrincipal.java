package gov.va.api.lighthouse.charon.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/** Contains principal information related to a rpc request. */
@Data
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RpcPrincipal implements IsPrincipal {
  /** Required for standard user, application proxy user. */
  @NotBlank @NonNull private String accessCode;
  /** Required for standard user, application proxy user. */
  @NotBlank @NonNull private String verifyCode;
  /** Required for application proxy user. */
  private String applicationProxyUser;
  /**
   * For site specific principals, a different RPC context can be specified. This field is not valid
   * for the default RPC request principal.
   */
  private String contextOverride;

  @Builder(builderMethodName = "standardUserBuilder", builderClassName = "StandardUserBuilder")
  private RpcPrincipal(
      @NonNull String accessCode, @NonNull String verifyCode, String contextOverride) {
    this.accessCode = accessCode;
    this.verifyCode = verifyCode;
    this.contextOverride = contextOverride;
  }

  @JsonCreator
  @Builder(
      builderMethodName = "applicationProxyUserBuilder",
      builderClassName = "ApplicationProxyUserBuilder")
  private RpcPrincipal(
      @JsonProperty("accessCode") @NonNull String accessCode,
      @JsonProperty("verifyCode") @NonNull String verifyCode,
      @JsonProperty("applicationProxyUser") String applicationProxyUser,
      @JsonProperty("contextOverride") String contextOverride) {
    this.accessCode = accessCode;
    this.verifyCode = verifyCode;
    this.applicationProxyUser = applicationProxyUser;
    this.contextOverride = contextOverride;
  }
}
