package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGateway.deoctothorpe;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/** Java representation of the LHS LIGHTHOUSE RPC GATEWAY RPC GETS manifest. */
@NoArgsConstructor(staticName = "create")
public class LhsLighthouseRpcGatewayGetsManifest
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayGetsManifest.Request, LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  private static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

  /** Build an RPC Request using field names. */
  @Data
  @Builder
  public static class Request implements TypeSafeRpcRequest {
    @Builder.Default String debugMode = "1";

    @NonNull String file;

    @NonNull String iens;

    @NonNull List<String> fields;

    List<GetsManifestFlags> flags;

    @Override
    public RpcDetails asDetails() {
      List<String> parameters = new ArrayList<>(6);
      parameters.add("debugmode^" + debugMode());
      parameters.add("api^manifest^gets");
      parameters.add("param^FILE^literal^" + file());
      parameters.add("param^IENS^literal^" + iens());
      parameters.add("param^FIELDS^literal^" + join(";", deoctothorpe(fields())));
      parameters.add(
          "param^FLAGS^literal^"
              + flags().stream().map(GetsManifestFlags::flag).collect(joining("")));
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }

    /** Lazy Initializer. */
    List<GetsManifestFlags> flags() {
      if (flags == null) {
        flags = List.of();
      }
      return flags;
    }

    @AllArgsConstructor
    public enum GetsManifestFlags {
      RETURN_EXTERNAL_VALUES("E"),
      RETURN_INTERNAL_VALUES("I"),
      OMIT_NULL_VALUES("N"),
      USE_FIELD_NAMES("R"),
      INCLUDE_ZERO_NODES("Z"),
      USE_AUDIT_TRAIL("A#");

      @Getter private final String flag;
    }
  }
}
