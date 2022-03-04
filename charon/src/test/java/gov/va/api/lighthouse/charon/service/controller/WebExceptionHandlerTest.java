package gov.va.api.lighthouse.charon.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import gov.va.api.lighthouse.charon.api.UnknownVista;
import gov.va.api.lighthouse.charon.service.core.UnrecoverableVistalinkExceptions;
import gov.va.api.lighthouse.charon.service.core.UnrecoverableVistalinkExceptions.LoginFailure;
import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

@Slf4j
public class WebExceptionHandlerTest {
  WebExceptionHandler exceptionHandler = new WebExceptionHandler();

  @SuppressWarnings("unused")
  public static Stream<Arguments> expectStatus() {
    return Stream.of(
        arguments(HttpStatus.BAD_REQUEST, new HttpMessageConversionException("FUGAZI")),
        arguments(HttpStatus.UNAUTHORIZED, new LoginFailure("FUGAZI")),
        arguments(HttpStatus.REQUEST_TIMEOUT, new TimeoutException("FUGAZI")),
        arguments(HttpStatus.BAD_REQUEST, new UnknownVista("FUGAZI")),
        arguments(
            HttpStatus.FORBIDDEN,
            new UnrecoverableVistalinkExceptions.BadRpcContext("FUGAZI", new Throwable("FUGAZI"))));
  }

  private ExceptionHandlerExceptionResolver createExceptionResolver() {
    ExceptionHandlerExceptionResolver exceptionResolver =
        new ExceptionHandlerExceptionResolver() {
          @Override
          protected ServletInvocableHandlerMethod getExceptionHandlerMethod(
              HandlerMethod handlerMethod, @NonNull Exception ex) {
            Method method =
                new ExceptionHandlerMethodResolver(WebExceptionHandler.class).resolveMethod(ex);
            assertThat(method).isNotNull();
            return new ServletInvocableHandlerMethod(exceptionHandler, method);
          }
        };
    exceptionResolver
        .getMessageConverters()
        .add(new MappingJackson2HttpMessageConverter(JacksonConfig.createMapper()));
    return exceptionResolver;
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource
  void expectStatus(HttpStatus httpStatus, Exception e) {
    MockMvc mockMvc =
        MockMvcBuilders.standaloneSetup(new FugaziController(e))
            .setHandlerExceptionResolvers(createExceptionResolver())
            .setMessageConverters(
                new MappingJackson2HttpMessageConverter(JacksonConfig.createMapper()))
            .build();

    var response =
        mockMvc
            .perform(get("/hi").contentType("application/json"))
            .andExpect(status().is(httpStatus.value()))
            .andExpect(jsonPath("error", notNullValue()))
            .andReturn()
            .getResponse();
    log.error("response: {}", response.getContentAsString());
  }

  @AllArgsConstructor
  @RestController
  static class FugaziController {
    Throwable e;

    @SneakyThrows
    @GetMapping("/hi")
    String hi() {
      if (e != null) {
        throw e;
      }
      return "hi";
    }
  }
}
