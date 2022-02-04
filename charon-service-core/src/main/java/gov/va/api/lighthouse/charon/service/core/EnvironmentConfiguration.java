package gov.va.api.lighthouse.charon.service.core;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvironmentConfiguration {

  /** The default number of retries we should make when attempting to invoke an RPC. */
  public static int defaultRetries() {
    return systemPropertyOrEnvironmentVariable("charon.vista.retries", 1);
  }

  /** The amount of time to wait before timing out. */
  public static int defaultTimeoutMillis() {
    return systemPropertyOrEnvironmentVariable("charon.vista.timeout-millis", 28000);
  }

  /**
   * Get a system property, environment property, or a default value. The default will be used if
   * the property value cannot be parsed.
   *
   * <p>The input system property is translated to an environment variable by replacing all
   * non-alphanumeric characters into "_" and making all other values upper case.
   */
  public static int systemPropertyOrEnvironmentVariable(String systemProperty, int defaultValue) {
    String maybeInt =
        systemPropertyOrEnvironmentVariable(systemProperty, Integer.toString(defaultValue));
    try {
      return Integer.parseInt(maybeInt);
    } catch (NumberFormatException e) {
      log.error(
          "Configuration {} value '{}' is not an integer value. Using {}",
          systemProperty,
          maybeInt,
          defaultValue);
      return defaultValue;
    }
  }

  /**
   * Get a system property, environment property, or a default value.
   *
   * <p>The input system property is translated to an environment variable by replacing all
   * non-alphanumeric characters into "_" and making all other values upper case.
   */
  public static String systemPropertyOrEnvironmentVariable(
      String systemProperty, String defaultValue) {
    var maybeProperty = Optional.ofNullable(trimToNull(System.getProperty(systemProperty)));
    if (maybeProperty.isPresent()) {
      return maybeProperty.get();
    }
    var environmentVariable = systemProperty.replaceAll("[^A-Za-z0-9]", "_").toUpperCase();
    maybeProperty = Optional.ofNullable(trimToNull(System.getenv(environmentVariable)));
    return maybeProperty.orElse(defaultValue);
  }
}
