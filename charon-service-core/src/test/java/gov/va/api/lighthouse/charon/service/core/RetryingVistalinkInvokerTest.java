package gov.va.api.lighthouse.charon.service.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.va.api.lighthouse.charon.api.RpcDetails;
import gov.va.med.exception.FoundationsException;
import gov.va.med.net.VistaSocketTimeOutException;
import gov.va.med.vistalink.adapter.cci.VistaLinkResourceException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class RetryingVistalinkInvokerTest {

  @Mock VistalinkInvoker delegate;

  static Stream<Arguments> retryOnException() {
    return Stream.of(
        Arguments.of(new SocketTimeoutException()),
        Arguments.of(new ConnectException()),
        Arguments.of(new RuntimeException(new RuntimeException(new SocketTimeoutException()))),
        Arguments.of(new RuntimeException(new RuntimeException(new ConnectException()))),
        Arguments.of(
            new VistaLinkResourceException(
                new FoundationsException(
                    new VistaSocketTimeOutException(new SocketTimeoutException())))));
  }

  @Test
  void buildRequestAndInvokeDelegatesSuccessfulCall() {
    VistalinkXmlResponse response = new VistalinkXmlResponse();
    when(delegate.buildRequestAndInvoke(any(RpcDetails.class))).thenReturn(response);
    var actual = RetryingVistalinkInvoker.wrap(delegate).buildRequestAndInvoke(new RpcDetails());
    assertThat(actual).isSameAs(response);
  }

  @ParameterizedTest
  @MethodSource("retryOnException")
  void buildRequestAndInvokeReturnsResponseIfRetriesSucceeds(Exception e) {
    VistalinkXmlResponse response = new VistalinkXmlResponse();
    when(delegate.buildRequestAndInvoke(any(RpcDetails.class)))
        .thenAnswer(throwCheckedException(e))
        .thenAnswer(throwCheckedException(e))
        .thenReturn(response);
    var actual = RetryingVistalinkInvoker.wrap(delegate).buildRequestAndInvoke(new RpcDetails());
    assertThat(actual).isSameAs(response);
  }

  @ParameterizedTest
  @MethodSource("retryOnException")
  void buildRequestAndInvokeThrowsIfRetriesFails(Exception e) {
    VistalinkXmlResponse response = new VistalinkXmlResponse();
    when(delegate.buildRequestAndInvoke(any(RpcDetails.class)))
        .thenAnswer(throwCheckedException(e));
    assertThatExceptionOfType(e.getClass())
        .isThrownBy(
            () -> RetryingVistalinkInvoker.wrap(delegate).buildRequestAndInvoke(new RpcDetails()));
  }

  @Test
  @SneakyThrows
  void invokeDelegatesSuccessfulCall() {
    RpcResponse response = mock(RpcResponse.class);
    when(delegate.invoke(any(RpcRequest.class))).thenReturn(response);
    var actual = RetryingVistalinkInvoker.wrap(delegate).invoke(RpcRequestFactory.getRpcRequest());
    assertThat(actual).isSameAs(response);
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource("retryOnException")
  void invokeReturnsResponseIfRetriesSucceeds(Exception e) {
    RpcResponse response = mock(RpcResponse.class);
    when(delegate.invoke(any(RpcRequest.class)))
        .thenAnswer(throwCheckedException(e))
        .thenAnswer(throwCheckedException(e))
        .thenReturn(response);
    var actual = RetryingVistalinkInvoker.wrap(delegate).invoke(RpcRequestFactory.getRpcRequest());
    assertThat(actual).isSameAs(response);
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource("retryOnException")
  void invokeReturnsResponseThrowsIfRetriesFails(Exception e) {
    RpcResponse response = mock(RpcResponse.class);
    when(delegate.invoke(any(RpcRequest.class))).thenAnswer(throwCheckedException(e));
    assertThatExceptionOfType(e.getClass())
        .isThrownBy(
            () ->
                RetryingVistalinkInvoker.wrap(delegate).invoke(RpcRequestFactory.getRpcRequest()));
  }

  private Answer<Object> throwCheckedException(Exception e) {
    return invocationOnMock -> {
      throw e;
    };
  }
}
