package de.tvneheim.scoreboardfx.model;

import lombok.With;

import java.time.Duration;

@With
public record Time(
    boolean paused,
    Duration elapsedTime
) {

  public Time() {
    this(true, Duration.ZERO);
  }

}
