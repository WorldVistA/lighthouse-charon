package gov.va.api.lighthouse.charon.api;

import static gov.va.api.lighthouse.charon.api.ConnectionDetailsParser.asConnectionDetails;
import static gov.va.api.lighthouse.charon.api.ConnectionDetailsParser.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ConnectionDetailsParserTest {
  @ParameterizedTest
  @ValueSource(
      strings = {
        "host:1234",
        "host:nope:div",
        "host:1234:div:timezone:rando",
        "host:1234:div:Space/Moon",
        "awesome.com:nope:567:America/New_York"
      })
  @NullAndEmptySource
  void asConnectionDetailsThrowsExceptionWhenValueCannotBeParsed(String value) {
    assertThat(parse(value)).isEmpty();
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> asConnectionDetails("n", value));
  }

  @Test
  void parses() {
    var spec = "awesome.com:1234:567:America/New_York";
    assertThat(asConnectionDetails("foo", spec))
        .isEqualTo(
            ConnectionDetails.builder()
                .name("foo")
                .host("awesome.com")
                .port(1234)
                .divisionIen("567")
                .timezone("America/New_York")
                .build());
    assertThat(parse(spec).get())
        .isEqualTo(
            ConnectionDetails.builder()
                .name(spec)
                .host("awesome.com")
                .port(1234)
                .divisionIen("567")
                .timezone("America/New_York")
                .build());
  }
}
