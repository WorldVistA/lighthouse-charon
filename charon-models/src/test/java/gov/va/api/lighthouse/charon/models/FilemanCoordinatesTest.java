package gov.va.api.lighthouse.charon.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FilemanCoordinatesTest {

  @Test
  void okCoverageYourMove() {
    assertThat(FilemanCoordinates.of("1", "2")).isEqualTo(new FilemanCoordinates("1", "2"));
  }
}
