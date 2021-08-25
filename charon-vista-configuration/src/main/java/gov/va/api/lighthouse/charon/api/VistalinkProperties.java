package gov.va.api.lighthouse.charon.api;

import static java.util.stream.Collectors.toSet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

/** Contains connection information about all known vista sites. */
@SuppressWarnings("ClassCanBeRecord")
@Builder
@Value
@Schema
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class VistalinkProperties {
  @Schema List<ConnectionDetails> vistas;

  /** Return a set of names for all known vista instances. */
  public Set<String> names() {
    return vistas.stream().map(ConnectionDetails::name).collect(toSet());
  }

  /** Always return a list of vistas. This is potentially empty. */
  public List<ConnectionDetails> vistas() {
    if (vistas == null) {
      return List.of();
    }
    return vistas;
  }
}
