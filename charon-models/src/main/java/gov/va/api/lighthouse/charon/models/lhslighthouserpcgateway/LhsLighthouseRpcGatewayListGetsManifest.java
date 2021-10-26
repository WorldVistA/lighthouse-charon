package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway.LhsLighthouseRpcGateway.deserialize;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcInvocationResult;
import gov.va.api.lighthouse.charon.models.TypeSafeRpc;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcRequest;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/** Java representation of the LHS LIGHTHOUSE RPC GATEWAY RPC LISTGETS manifest. */
@NoArgsConstructor(staticName = "create")
public class LhsLighthouseRpcGatewayListGetsManifest
    implements TypeSafeRpc<
        LhsLighthouseRpcGatewayListGetsManifest.Request, LhsLighthouseRpcGatewayResponse> {
  public static final String RPC_NAME = "LHS LIGHTHOUSE RPC GATEWAY";

  public static final String DEFAULT_RPC_CONTEXT = "LHS RPC CONTEXT";

  @Override
  public TypeSafeRpcResponse fromResults(List<RpcInvocationResult> results) {
    var reader = new ObjectMapper();
    return LhsLighthouseRpcGatewayResponse.builder()
        .resultsByStation(
            results.stream()
                .filter(invocationResult -> invocationResult.error().isEmpty())
                .collect(toMap(r -> r.vista(), r -> deserialize(reader, r.response()))))
        .build();
  }

  /**
   * Build an RPC Request using field names.
   *
   * <p>The flags field is ignored by this RPC, and thus omitted.
   *
   * */
  @Data
  @Builder
  public static class Request implements TypeSafeRpcRequest {
    @Builder.Default String debugMode = "1";

    @NonNull String file;

    Optional<String> iens;

    List<String> fields;

    Optional<String> number;

    Optional<LhsLighthouseRpcGatewayListManifest.Request.From> from;

    Optional<String> part;

    Optional<String> index;

    Optional<String> screen;

    Optional<String> id;

    @Override
    public RpcDetails asDetails() {
      List<String> parameters = new ArrayList<>();
      parameters.add("debugmode^" + debugMode());
      parameters.add("api^manifest^listgets");
      parameters.add("param^FILE^literal^" + file());
      parameters.add("param^IENS^literal^" + iens().orElse(""));
      parameters.add(
          "param^FIELDS^literal^"
              + fields().stream().map(LhsLighthouseRpcGateway::deoctothorpe).collect(joining(";")));
      parameters.add("param^NUMBER^literal^" + number().orElse(""));
      if (from().isPresent()) {
        parameters.add("param^FROM^list^1^" + from().get().name());
        parameters.add("param^FROM^list^2^" + from().get().ien());
        parameters.add("param^FROM^list^IEN^" + from().get().ien());
      } else {
        parameters.add("param^FROM^literal^");
      }
      if (part().isPresent()) {
        parameters.add("param^PART^literal^" + part().get());
      } else {
        parameters.add("param^PART^literal^");
      }
      parameters.add("param^INDEX^literal^" + index().orElse(""));
      parameters.add("param^SCREEN^literal^" + screen().orElse(""));
      parameters.add("param^ID^literal^" + id().orElse(""));
      return RpcDetails.builder()
          .name(RPC_NAME)
          .context(DEFAULT_RPC_CONTEXT)
          .parameters(List.of(RpcDetails.Parameter.builder().array(parameters).build()))
          .build();
    }

    /** Lazy Initializer. */
    List<String> fields() {
      if (fields == null) {
        fields = List.of();
      }
      return fields;
    }

    /** Lazy Initializer. */
    Optional<LhsLighthouseRpcGatewayListManifest.Request.From> from() {
      if (from == null) {
        from = Optional.empty();
      }
      return from;
    }

    /** Lazy Initializer. */
    Optional<String> id() {
      if (id == null) {
        id = Optional.empty();
      }
      return id;
    }

    /** Lazy Initializer. */
    Optional<String> iens() {
      if (iens == null) {
        iens = Optional.empty();
      }
      return iens;
    }

    /** Lazy Initializer. */
    Optional<String> index() {
      if (index == null) {
        index = Optional.empty();
      }
      return index;
    }

    /** Lazy Initializer. */
    Optional<String> number() {
      if (number == null) {
        number = Optional.empty();
      }
      return number;
    }

    /** Lazy Initializer. */
    Optional<String> part() {
      if (part == null) {
        part = Optional.empty();
      }
      return part;
    }

    /** Lazy Initializer. */
    Optional<String> screen() {
      if (screen == null) {
        screen = Optional.empty();
      }
      return screen;
    }

    @Value
    @Builder
    public static class From {
      String name;

      String ien;
    }
  }
}
