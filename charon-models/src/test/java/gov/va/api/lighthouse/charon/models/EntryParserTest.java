package gov.va.api.lighthouse.charon.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class EntryParserTest {

  @Test
  void parseNewLineDelimited() {
    var p = new UpperEntryParse();
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> p.parseNewLineDelimited(null));
    assertThat(p.parseNewLineDelimited("aaa\nbbb\n\nccc")).containsExactly("AAA", "BBB", "", "CCC");
  }

  private class UpperEntryParse implements EntryParser<String> {

    @Override
    public String parseLine(String entry) {
      return entry == null ? "" : entry.toUpperCase(Locale.US);
    }
  }
}
