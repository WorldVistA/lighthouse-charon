package gov.va.api.lighthouse.charon.api.v1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/** Contains results of an RPC request. */
@Data
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RpcInvocationResultV1 {
  @NotNull private String vista;
  @NotNull private String timezone;
  @NotNull private String response;
}
