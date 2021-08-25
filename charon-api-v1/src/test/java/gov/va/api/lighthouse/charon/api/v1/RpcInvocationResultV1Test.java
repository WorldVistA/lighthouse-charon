package gov.va.api.lighthouse.charon.api.v1;

import static gov.va.api.lighthouse.charon.api.v1.RoundTrip.assertRoundTrip;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class RpcInvocationResultV1Test {
  @Test
  void lazyInitialization() {
    assertThat(RpcInvocationResultV1.builder().build().error()).isEqualTo(Optional.empty());
  }

  @Test
  void roundTrip() {
    var sample =
        RpcInvocationResultV1.builder()
            .vista("1")
            .timezone("US/New_York")
            .response("Sample Response")
            .error(Optional.of("Sample Error"))
            .build();
    assertRoundTrip(sample);
  }
}
