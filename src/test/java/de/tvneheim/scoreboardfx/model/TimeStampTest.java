package de.tvneheim.scoreboardfx.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeStampTest {

  @Test
  void difference() {
    TimeStamp t1 = new TimeStamp(0, 15, 32);
    TimeStamp t2 = new TimeStamp(0, 5, 34);
    var result = TimeStamp.difference(t1, t2);
    assertThat(result.minutes()).isEqualTo(1);
    assertThat(result.seconds()).isEqualTo(50);
    assertThat(result.millis()).isEqualTo(0);
  }
}