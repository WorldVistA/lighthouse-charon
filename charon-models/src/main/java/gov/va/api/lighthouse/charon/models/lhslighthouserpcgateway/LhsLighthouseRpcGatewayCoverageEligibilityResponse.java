package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

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
public class LhsLighthouseRpcGatewayCoverageEligibilityResponse
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayCoverageEligibilityResponse.Request,
        LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  private static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

  /** Build an RPC Request using field names. */
  @Data
  @Builder
  @AllArgsConstructor
  public static class Request implements TypeSafeRpcRequest {
    @Builder.Default private String debugMode = "1";

    private String iens;

    @NonNull private RequestType requestType;

    @NonNull private PatientId patientId;

    /** Build an RPC Read Request using field names. */
    @Builder(builderClassName = "ReadRequestBuilder", builderMethodName = "read")
    public Request(String debugMode, @NonNull PatientId patientId, @NonNull String iens) {
      this.debugMode = debugMode == null ? "1" : debugMode;
      this.requestType = RequestType.READ;
      this.patientId = patientId;
      this.iens = iens;
    }

    /** Build an RPC Search Request using field names. */
    @Builder(builderClassName = "SearchRequestBuilder", builderMethodName = "search")
    public Request(String debugMode, @NonNull PatientId patientId) {
      this.debugMode = debugMode == null ? "1" : debugMode;
      this.requestType = RequestType.SEARCH;
      this.patientId = patientId;
    }

    @Override
    public RpcDetails asDetails() {
      List<String> parameters = new ArrayList<>(3);
      parameters.add("debugmode^" + debugMode());
      parameters.add(requestType.routineName());
      parameters.add("lhsdfn^" + patientId());
      if (RequestType.READ == requestType()) {
        parameters.add(InsuranceType.FILE_NUMBER + "^1^IEN^" + iens());
      }
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }

    @AllArgsConstructor
    enum RequestType {
      READ("api^read^cer"),
      SEARCH("api^search^cer");

      @Getter private final String routineName;
    }
  }
}
