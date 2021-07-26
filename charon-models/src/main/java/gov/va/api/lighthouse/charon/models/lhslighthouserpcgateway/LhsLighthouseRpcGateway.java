package gov.va.api.lighthouse.charon.models.lhslighthouserpcgateway;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LhsLighthouseRpcGateway {
  /**
   * Return a list of 1 item by removing the `#` from the field number prefix if it exists and
   * append a wildcard.
   */
  public static List<String> allFieldsOfSubfile(@NonNull String subfileFieldNumber) {
    return List.of(deoctothorpe(subfileFieldNumber) + "*");
  }

  /** Remove the `#` from the field number prefix if it exists. */
  public static String deoctothorpe(@NonNull String fieldNumber) {
    if (fieldNumber.length() > 0 && fieldNumber.charAt(0) == '#') {
      return fieldNumber.substring(1);
    }
    return fieldNumber;
  }

  /** Remove the `#` from the field number prefix if it exists. */
  public static List<String> deoctothorpe(@NonNull List<String> fieldNumbers) {
    return fieldNumbers.stream().map(LhsLighthouseRpcGateway::deoctothorpe).collect(toList());
  }
}
