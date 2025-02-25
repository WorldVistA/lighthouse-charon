package gov.va.api.lighthouse.charon.service.core.macro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MacroProcessorFactoryTest {

  @Mock MacroExecutionContext executionContext;

  @Test
  void macroProcessorIsConfiguredCorrectly() {
    var macroProcessorFactory = new MacroProcessorFactory(FugaziMacros.testMacros());
    MacroProcessor mp = macroProcessorFactory.create(executionContext);
    assertThat(mp.macros()).containsExactlyInAnyOrderElementsOf(FugaziMacros.testMacros());
    assertThat(mp.macroExecutionContext()).isEqualTo(executionContext);
  }
}
