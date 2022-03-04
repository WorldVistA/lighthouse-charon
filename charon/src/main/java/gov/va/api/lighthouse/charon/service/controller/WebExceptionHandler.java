package gov.va.api.lighthouse.charon.service.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import gov.va.api.lighthouse.charon.api.UnknownVista;
import gov.va.api.lighthouse.charon.api.v1.ErrorResponseV1;
import gov.va.api.lighthouse.charon.service.core.UnrecoverableVistalinkExceptions;
import gov.va.api.lighthouse.charon.service.core.UnrecoverableVistalinkExceptions.LoginFailure;
import java.util.concurrent.TimeoutException;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Catch thrown exceptions, and return appropriate responses. */
@Slf4j
@RestControllerAdvice
@RequestMapping(produces = {"application/json"})
public class WebExceptionHandler {
  @SneakyThrows
  private ErrorResponseV1 failedResponseFor(String message) {
    ErrorResponseV1 response = new ErrorResponseV1(isBlank(message) ? "unknown" : message);
    log.error("Response: {}", JacksonConfig.createMapper().writeValueAsString(response));
    return response;
  }

  @ExceptionHandler({HttpMessageConversionException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseV1 handleBadRequestBody(Exception e, HttpServletRequest request) {
    log.error("Bad request", e);
    return failedResponseFor("Failed to read request body.");
  }

  @ExceptionHandler({UnrecoverableVistalinkExceptions.BadRpcContext.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponseV1 handleBadRpcContext(Exception e, HttpServletRequest request) {
    log.error("Bad RPC Context", e);
    return failedResponseFor("RPC is not registered to the chosen RPC context.");
  }

  @ExceptionHandler({LoginFailure.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponseV1 handleFailedLogin(Exception e, HttpServletRequest request) {
    log.error("Login failed", e);
    return failedResponseFor(e.getMessage());
  }

  @ExceptionHandler({TimeoutException.class})
  @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
  public ErrorResponseV1 handleRequestTimeout(Exception e, HttpServletRequest request) {
    return failedResponseFor("Request timed out.");
  }

  @ExceptionHandler({UnknownVista.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseV1 handleUnknownVistaIncludes(Exception e, HttpServletRequest request) {
    return failedResponseFor("Unknown vista site specified: " + e.getMessage());
  }
}
