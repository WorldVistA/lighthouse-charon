package gov.va.api.lighthouse.charon.service.controller;

import gov.va.api.lighthouse.charon.models.FilemanDate;
import gov.va.api.lighthouse.charon.service.core.macro.Macro;
import gov.va.api.lighthouse.charon.service.core.macro.MacroExecutionContext;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

/** Macro for processing ISO-8601 dates into fileman dates for use in vista. */
@Component
public class LocalDateMacro implements Macro {

  @Override
  public String evaluate(MacroExecutionContext ctx, String value) {
    try {
      return FilemanDate.from(Instant.parse(value))
          .formatAsDateTime(ZoneId.of(ctx.connectionDetails().timezone()));
    } catch (DateTimeParseException e) {
      throw new LocalDateMacroParseFailure(
          String.format("Failed to parse parameter as ISO-8601 date: %s", value));
    }
  }

  @Override
  public String name() {
    return "local-fileman-date";
  }

  static class LocalDateMacroParseFailure extends IllegalArgumentException {
    public LocalDateMacroParseFailure(String message) {
      super(message);
    }
  }
}
