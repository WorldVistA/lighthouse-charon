package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  /**
   * Return an empty map if there are no errors, otherwise return a simplified error message per
   * station. (key: station, value: message).
   */
  public Map<String, String> collectErrors() {
    Map<String, String> errors = new HashMap<>(resultsByStation().size());
    resultsByStation()
        .forEach(
            (station, result) -> {
              if (result.hasError()) {
                String message = result.error().data().toString();
                errors.put(station, message);
              }
            });
    return errors;
  }

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
     * Attempt to convert the external value if present, throwing an UnexpectedVistaValue if
     * conversion fails.
     */
    public <T> Optional<T> external(@NonNull String fieldNumber, Function<String, T> converter) {
      return convert(external(fieldNumber), fieldNumber, converter);
    }

    /**
     * Attempt to apply the given map to the external value, throwing an UnexpectedVistaValue if
     * value is not within the map.
     */
    public <T> Optional<T> external(
        @NonNull String fieldNumber, Map<String, T> supportedEnumeratedValues) {
      return map(external(fieldNumber), fieldNumber, supportedEnumeratedValues);
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

    /**
     * Attempt to apply the given map to the internal value, throwing an UnexpectedVistaValue if
     * value is not within the map.
     */
    public <T> Optional<T> internal(
        @NonNull String fieldNumber, Map<String, T> supportedEnumeratedValues) {
      return map(internal(fieldNumber), fieldNumber, supportedEnumeratedValues);
    }

    private <T> Optional<T> map(
        Optional<String> fieldValue, String fieldNumber, Map<String, T> supportedEnumeratedValues) {
      if (fieldValue.isEmpty()) {
        return Optional.empty();
      }
      var mapping = supportedEnumeratedValues.get(fieldValue.get());
      if (mapping == null) {
        throw new UnexpectedVistaValue(
            fieldNumber,
            fieldValue.get(),
            "Supported enumeration values: " + supportedEnumeratedValues);
      }
      return Optional.of(mapping);
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

    private List<FilemanEntry> results;
    private ResultsError error;

    @JsonIgnore
    public boolean hasError() {
      return error != null;
    }

    /** Lazy Initialization. */
    public List<FilemanEntry> results() {
      if (results == null) {
        return List.of();
      }
      return results;
    }
  }

  /**
   * Support deserialization of any data in 'error' field of the response by collecting into a map..
   *
   * <pre>
   * Version 1
   * {
   *   error: message,
   *   data: {
   *     key: value,
   *     key: value,
   *     ...
   *   }
   * }
   * Version 2
   * {
   *   code: number,
   *   location: string,
   *   text: string
   * }
   * </pre>
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonAutoDetect(
      fieldVisibility = JsonAutoDetect.Visibility.ANY,
      isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  public static class ResultsError {
    /** V1 of result error had a data field, now it has just a map of anything. */
    private Map<String, String> data;

    @JsonAnySetter
    public void data(String key, String value) {
      data().put(key, value);
    }

    /** Lazy initialization. */
    public Map<String, String> data() {
      if (data == null) {
        return new HashMap<>();
      }
      return data;
    }

    /** Backwards compatibility for older version of structure. */
    public String error() {
      return data().toString();
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
