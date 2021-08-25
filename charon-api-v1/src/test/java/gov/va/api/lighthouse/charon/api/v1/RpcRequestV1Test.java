package gov.va.api.lighthouse.charon.api.v1;

import static gov.va.api.lighthouse.charon.api.v1.RoundTrip.assertRoundTrip;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RpcRequestV1Test {
  private static RpcRequestV1 request0() {
    return RpcRequestV1.builder()
        .vista("123")
        .principal(RpcPrincipalV1.builder().accessCode("ac").verifyCode("vc").build())
        .rpc(
            RpcDetailsV1.builder()
                .name("FAUX NAME")
                .context("FAUX CONTEXT")
                .parameters(
                    List.of(
                        RpcDetailsV1.Parameter.builder().string("").build(),
                        RpcDetailsV1.Parameter.builder().string("a").build(),
                        RpcDetailsV1.Parameter.builder().ref("").build(),
                        RpcDetailsV1.Parameter.builder().ref("b").build(),
                        RpcDetailsV1.Parameter.builder().array(List.of()).build(),
                        RpcDetailsV1.Parameter.builder().array(List.of("c")).build(),
                        RpcDetailsV1.Parameter.builder().namedArray(Map.of()).build(),
                        RpcDetailsV1.Parameter.builder().namedArray(Map.of("d", "e")).build()))
                .build())
        .build();
  }

  private static RpcRequestV1 request1() {
    return RpcRequestV1.builder()
        .vista("123")
        .principal(RpcPrincipalV1.builder().accessCode("ac").verifyCode("vc").build())
        .rpc(
            RpcDetailsV1.builder()
                .name("FAUX NAME")
                .context("FAUX CONTEXT")
                .parameters(
                    List.of(
                        RpcDetailsV1.Parameter.builder().string("").build(),
                        RpcDetailsV1.Parameter.builder().string("a").build(),
                        RpcDetailsV1.Parameter.builder().ref("").build(),
                        RpcDetailsV1.Parameter.builder().ref("b").build(),
                        RpcDetailsV1.Parameter.builder().array(List.of()).build(),
                        RpcDetailsV1.Parameter.builder().array(List.of("c")).build(),
                        RpcDetailsV1.Parameter.builder().namedArray(Map.of()).build(),
                        RpcDetailsV1.Parameter.builder().namedArray(Map.of("d", "e")).build()))
                .build())
        .build();
  }

  static Stream<Arguments> roundTrip() {
    return Stream.of(arguments(request0()), arguments(request1()));
  }

  @ParameterizedTest
  @MethodSource
  void roundTrip(RpcRequestV1 sample) {
    assertRoundTrip(sample);
  }
}
