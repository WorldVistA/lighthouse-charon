package gov.va.api.lighthouse.charon.api.v1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.va.api.lighthouse.charon.api.IsPrincipal;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/** Contains principal information related to a rpc request. */
@Data
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RpcPrincipalV1 implements IsPrincipal {
  /** Required for standard user, application proxy user. */
  @NotBlank @NonNull private String accessCode;
  /** Required for standard user, application proxy user. */
  @NotBlank @NonNull private String verifyCode;
  /** Required for application proxy user. */
  private String applicationProxyUser;

  @Builder(builderMethodName = "forAvCodes", builderClassName = "AvCodesBuilder")
  private RpcPrincipalV1(@NonNull String accessCode, @NonNull String verifyCode) {
    this.accessCode = accessCode;
    this.verifyCode = verifyCode;
  }

  @JsonCreator
  @Builder(
      builderMethodName = "forApplicationProxyUser",
      builderClassName = "ApplicationProxyUserBuilder")
  private RpcPrincipalV1(
      @JsonProperty("accessCode") @NonNull String accessCode,
      @JsonProperty("verifyCode") @NonNull String verifyCode,
      @JsonProperty("applicationProxyUser") String applicationProxyUser) {
    this.accessCode = accessCode;
    this.verifyCode = verifyCode;
    this.applicationProxyUser = applicationProxyUser;
  }
}
