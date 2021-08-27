package gov.va.api.lighthouse.charon.api.v1;

import static gov.va.api.lighthouse.charon.api.v1.RoundTrip.assertRoundTrip;

import org.junit.jupiter.api.Test;

public class RpcInvocationResultV1Test {

  @Test
  void roundTrip() {
    var sample =
        RpcInvocationResultV1.builder()
            .vista("1")
            .timezone("US/New_York")
            .response("Sample Response")
            .build();
    assertRoundTrip(sample);
  }
}
