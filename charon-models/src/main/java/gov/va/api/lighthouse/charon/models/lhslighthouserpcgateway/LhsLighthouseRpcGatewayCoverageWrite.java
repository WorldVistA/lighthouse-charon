package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGateway.deserialize;
import static java.lang.String.join;
import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(staticName = "create")
public class LhsLighthouseRpcGatewayCoverageWrite
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayCoverageWrite.Request, LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  public static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

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

    @NonNull CoverageWriteApi api;

    PatientId patient;

    @NonNull Set<WriteableFilemanValue> fields;

    @Override
    public RpcDetails asDetails() {
      List<String> parameters = new ArrayList<>(3);
      parameters.add("debugmode^" + debugMode());
      parameters.add(api().value());
      if (patient() != null) {
        parameters.add("lhsdfn^" + patient().toString());
      }
      parameters.addAll(fields().stream().map(WriteableFilemanValue::toString).toList());
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }

    @AllArgsConstructor
    public enum CoverageWriteApi {
      CREATE("api^create^coverage"),
      UPDATE("api^update^coverage");

      @Getter private final String value;
    }
  }

  @Data
  @Builder
  public static class WriteableFilemanValue {
    @NonNull private String file;
    @NonNull private String field;
    @NonNull private Integer index;
    @NonNull private String value;

    @Override
    public String toString() {
      return join("^", file(), "" + index(), field(), value());
    }
  }
}
