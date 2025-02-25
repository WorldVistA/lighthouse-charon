package gov.va.api.lighthouse.charon.models.xobvtestping;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcResponse;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Helper class for invoking the XobvTestPing rpc. */
@NoArgsConstructor(staticName = "create")
public class XobvTestPing implements TypeSafeRpc<XobvTestPing.Request, XobvTestPing.Response> {

  public static final String RPC_NAME = "XOBV TEST PING";

  private static final String DEFAULT_RPC_CONTEXT = "XOBV VISTALINK TESTER";

  /** Type safe Request class for XobvTestPing requests. */
  @Builder
  public static class Request implements TypeSafeRpcRequest {
    private Optional<String> context;

    /** Build RpcDetails out of the request. */
    @Override
    public RpcDetails asDetails() {
      return RpcDetails.builder()
          .context(context().orElse(DEFAULT_RPC_CONTEXT))
          .name(RPC_NAME)
          .build();
    }

    /** Lazy Initializer. */
    @SuppressWarnings("OptionalAssignedToNull")
    Optional<String> context() {
      if (context == null) {
        context = Optional.empty();
      }
      return context;
    }
  }

  /** Type safe Response class for XobvTestPing responses. */
  @Data
  @Builder
  public static class Response implements TypeSafeRpcResponse {
    private Map<String, String> resultsByStation;
  }
}
