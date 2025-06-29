package de.tvneheim.scoreboardfx.model;

import lombok.With;

@With
public record Time(
    boolean paused,
    int period,
    int minutes,
    int seconds,
    int millis
) {

  public Time() {
    this(true, 1, 0, 0, 0);
  }



  public String asFormattedTime() {
    return String.format("%02d:%02d", minutes, seconds);
  }
}
