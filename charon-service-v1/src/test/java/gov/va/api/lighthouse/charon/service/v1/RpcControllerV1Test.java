package gov.va.api.lighthouse.charon.service.v1;

import static gov.va.api.lighthouse.charon.service.v1.Samples.connectionDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.RpcDetails.Parameter;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalV1;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.core.EncryptedLogging;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RpcControllerV1Test {
  @Mock VistaResolver vistaResolver;
  @Mock EncryptedLogging encryptedLogging;
  @Mock RpcInvokerFactoryV1 invokerFactory;
  @Mock RpcInvokerV1 invoker;

  RpcControllerV1 _controller() {
    return new RpcControllerV1(vistaResolver, encryptedLogging, invokerFactory);
  }

  private RpcRequestV1 _request() {
    return RpcRequestV1.builder()
        .principal(
            RpcPrincipalV1.builder()
                .applicationProxyUser("apu")
                .accessCode("ac")
                .verifyCode("vc")
                .build())
        .vista("v1")
        .rpc(
            RpcDetails.builder()
                .name("RPC NAME")
                .context("RPC CTX")
                .parameters(List.of(Parameter.builder().string("P1").build()))
                .build())
        .build();
  }

  private RpcInvocationResultV1 _results() {
    return RpcInvocationResultV1.builder()
        .vista("123")
        .timezone("America/New_York")
        .response("fugazi")
        .build();
  }

  @Test
  void invokes() {
    var request = _request();
    var connectionDetails = connectionDetail(1);
    var results = _results();
    when(vistaResolver.resolve("v1")).thenReturn(connectionDetails);
    when(invokerFactory.create(request, connectionDetails)).thenReturn(invoker);
    when(invoker.invoke()).thenReturn(results);
    assertThat(_controller().invoke(request)).isEqualTo(results);
    verify(invoker).close();
  }
}
