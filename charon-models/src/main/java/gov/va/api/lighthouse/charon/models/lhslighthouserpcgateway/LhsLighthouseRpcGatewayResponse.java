package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gov.va.api.lighthouse.charon.models.TypeSafeRpcResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/** Java model of the Lighthouse Gateway RPC's string response for (de)serialization. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class LhsLighthouseRpcGatewayResponse implements TypeSafeRpcResponse {
  private Map<String, Results> resultsByStation;

  /** Lazy Initialization. */
  public Map<String, Results> resultsByStation() {
    if (resultsByStation == null) {
      resultsByStation = Map.of();
    }
    return resultsByStation;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  public static class FilemanEntry {
    private Map<String, Values> fields;

    private String file;

    private String ien;

    private static Optional<String> nonBlankValue(String value) {
      if (StringUtils.isBlank(value)) {
        return Optional.empty();
      }
      return Optional.of(value);
    }

    private <T> Optional<T> convert(
        Optional<String> fieldValue, String fieldNumber, Function<String, T> converter) {
      if (fieldValue.isEmpty()) {
        return Optional.empty();
      }
      try {
        return fieldValue.map(converter);
      } catch (Exception e) {
        throw new UnexpectedVistaValue(fieldNumber, fieldValue.get(), e);
      }
    }

    /**
     * Return an optional of the external value for the field if it is present and not blank,
     * otherwise return empty.
     */
    public Optional<String> external(@NonNull String fieldNumber) {
      Values field = field(fieldNumber);
      if (field == null) {
        return Optional.empty();
      }
      return nonBlankValue(field.ext());
    }

    /**
     * Attempt to convert the internal value if present, throwing an UnexpectedVistaValue if
     * conversion fails.
     */
    public <T> Optional<T> external(@NonNull String fieldNumber, Function<String, T> converter) {
      return convert(external(fieldNumber), fieldNumber, converter);
    }

    /** Return null of the field is missing, otherwise return the field. */
    public Values field(@NonNull String fieldNumber) {
      return fields().get(fieldNumber);
    }

    /** Lazy Initialization. */
    public Map<String, Values> fields() {
      if (fields == null) {
        fields = new HashMap<>();
      }
      return fields;
    }

    /**
     * Return an optional of the external value for the field if it is present and not blank,
     * otherwise return empty.
     */
    public Optional<String> internal(@NonNull String fieldNumber) {
      Values field = field(fieldNumber);
      if (field == null) {
        return Optional.empty();
      }
      return nonBlankValue(field.in());
    }

    /**
     * Attempt to convert the internal value if present, throwing an UnexpectedVistaValue if
     * conversion fails.
     */
    public <T> Optional<T> internal(@NonNull String fieldNumber, Function<String, T> converter) {
      return convert(internal(fieldNumber), fieldNumber, converter);
    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  public static class Results {

    // ToDo add metadata

    private List<FilemanEntry> results;

    /** Lazy Initialization. */
    public List<FilemanEntry> results() {
      if (results == null) {
        return List.of();
      }
      return results;
    }
  }

  /**
   * A illegal argument exception that indicates the value from Vista is unexpected, e.g. receiving
   * a non-numeric value in a field that is 'guaranteed' to be a number.
   */
  public static class UnexpectedVistaValue extends IllegalArgumentException {
    public UnexpectedVistaValue(String field, Object unexpectedValue, Throwable cause) {
      super(String.format("Field %s = %s", field, unexpectedValue), cause);
    }

    public UnexpectedVistaValue(String field, Object unexpectedValue, String reason) {
      super(String.format("Field %s = %s: %s", field, unexpectedValue, reason));
    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor(staticName = "of")
  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  public static class Values {
    String ext;

    String in;
  }
}
