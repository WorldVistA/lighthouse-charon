package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/** Java representation of the LHS LIGHTHOUSE RPC GATEWAY RPC GETS manifest. */
@NoArgsConstructor(staticName = "create")
public class LhsLighthouseRpcGatewayCoverageSearch
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayCoverageSearch.Request, LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  private static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

  /** Build an RPC Request using field names. */
  @Data
  @Builder
  public static class Request implements TypeSafeRpcRequest {
    @Builder.Default String debugMode = "1";

    @NonNull private PatientId id;

    @Override
    public RpcDetails asDetails() {
      List<String> parameters = new ArrayList<>(3);
      parameters.add("debugmode^" + debugMode());
      parameters.add("api^search^coverage");
      parameters.add("lhsdfn^" + id());
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }
  }
}
