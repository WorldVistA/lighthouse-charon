package gov.va.api.lighthouse.charon.api.v1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/** Contains all information related to an RpcRequest. */
@Data
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RpcRequestV1 {
  @NotNull @Valid private RpcDetails rpc;
  @NotNull @Valid private RpcPrincipalV1 principal;

  @Schema(
      description = "The Vista site or coordinates",
      format = "site or host:port:divisionIen:timezoneId",
      example = "673 or 10.10.10.10:18673:673:America/New_York")
  @NotNull
  private String vista;
}
