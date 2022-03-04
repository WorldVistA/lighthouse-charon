package gov.va.api.lighthouse.charon.api.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Builder
@Jacksonized
public class ErrorResponseV1 {
  String error;
}
