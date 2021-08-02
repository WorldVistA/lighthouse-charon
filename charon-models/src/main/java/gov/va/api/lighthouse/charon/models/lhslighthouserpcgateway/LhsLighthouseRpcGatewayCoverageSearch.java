package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

/** Java representation of the LHS LIGHTHOUSE RPC GATEWAY RPC GETS manifest. */
@NoArgsConstructor(staticName = "create")
public class LhsLighthouseRpcGatewayCoverageSearch
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayCoverageSearch.Request, LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  private static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

  @SneakyThrows
  private LhsLighthouseRpcGatewayResponse.Results deserialize(ObjectMapper reader, String value) {
    return reader.readValue(value, LhsLighthouseRpcGatewayResponse.Results.class);
  }

  @Override
  public LhsLighthouseRpcGatewayResponse fromResults(List<RpcInvocationResult> results) {
    ObjectMapper reader = new ObjectMapper();
    return LhsLighthouseRpcGatewayResponse.builder()
        .resultsByStation(
            results.stream()
                .filter(invocationResult -> invocationResult.error().isEmpty())
                .collect(toMap(r -> r.vista(), r -> deserialize(reader, r.response()))))
        .build();
  }

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
      parameters.add("lhsdfn^" + id.toString());
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }

    /** Patient ID model. */
    @Value
    public static class PatientId {
      String dfn;
      String icn;

      /** You must specify dfn or icn. */
      private PatientId(String dfn, String icn) {
        this.dfn = dfn;
        this.icn = icn;
      }

      public static PatientId forDfn(String dfn) {
        return new PatientId(dfn, null);
      }

      public static PatientId forIcn(String icn) {
        return new PatientId(null, icn);
      }

      @Override
      public String toString() {
        return dfn != null ? dfn : ":" + icn;
      }
    }
  }
}
