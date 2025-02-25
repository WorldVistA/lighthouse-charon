package gov.va.api.lighthouse.charon.service.core.macro;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Provides access to fully configured macros. */
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class MacroProcessorFactory {

  private final @NonNull List<Macro> macros;

  /** Create a macro processor factory from a context and connectionDetails. */
  public MacroProcessor create(MacroExecutionContext ctx) {
    return MacroProcessor.builder().macros(macros).macroExecutionContext(ctx).build();
  }
}
