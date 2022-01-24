package gov.va.api.lighthouse.charon.service.v1;

import static gov.va.api.lighthouse.charon.service.v1.Samples.connectionDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcInvocationResultV1;
import gov.va.api.lighthouse.charon.service.core.VistalinkInvoker;
import gov.va.api.lighthouse.charon.service.core.VistalinkXmlResponse;
import gov.va.api.lighthouse.charon.service.core.VistalinkXmlResponse.Payload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DelegatingRpcInvokerV1Test {

  @Mock VistalinkInvoker delegate;

  @Test
  void closeDelegates() {
    var details = RpcDetails.builder().build();
    var invoker = DelegatingRpcInvokerV1.builder().invoker(delegate).details(details).build();
    invoker.close();
    verify(delegate).close();
  }

  @Test
  void invokesAndPackagesResponse() {
    var details = RpcDetails.builder().build();
    var invoker = DelegatingRpcInvokerV1.builder().invoker(delegate).details(details).build();
    VistalinkXmlResponse response = new VistalinkXmlResponse();
    response.setResponse(new Payload());
    response.getResponse().setValue("fugazi");
    when(delegate.buildRequestAndInvoke(details)).thenReturn(response);
    when(delegate.vista()).thenReturn("123");
    var connectionDetails = connectionDetail(1);
    when(delegate.connectionDetails()).thenReturn(connectionDetails);
    var result = invoker.invoke();
    assertThat(result)
        .isEqualTo(
            RpcInvocationResultV1.builder()
                .vista("123")
                .timezone(connectionDetails.timezone())
                .response("fugazi")
                .build());
  }
}
