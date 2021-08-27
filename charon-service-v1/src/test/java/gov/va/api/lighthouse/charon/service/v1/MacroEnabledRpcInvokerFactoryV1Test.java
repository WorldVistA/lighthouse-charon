package gov.va.api.lighthouse.charon.service.v1;

import static gov.va.api.lighthouse.charon.service.v1.Samples.connectionDetail;
import static gov.va.api.lighthouse.charon.service.v1.Samples.principal;
import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcRequestV1;
import gov.va.api.lighthouse.charon.service.core.macro.MacroProcessorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MacroEnabledRpcInvokerFactoryV1Test {
  @Mock MacroProcessorFactory macroProcessorFactory;

  @Test
  void yourBoiMakesAThing() {
    var factory = new MacroEnabledRpcInvokerFactoryV1(macroProcessorFactory);
    var connectionDetails = connectionDetail(1);
    var request =
        RpcRequestV1.builder()
            .vista(connectionDetails.name())
            .principal(principal("x"))
            .rpc(RpcDetails.builder().name("FUGAZI").context("FUGAZI CTX").build())
            .build();
    var invoker = (DelegatingRpcInvokerV1) factory.create(request, connectionDetails);
    assertThat(invoker.details()).isEqualTo(request.rpc());
    assertThat(invoker.invoker().connectionDetails()).isEqualTo(connectionDetails);
    assertThat(invoker.invoker().vista()).isEqualTo(connectionDetails.name());
  }
}
