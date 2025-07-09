package de.tvneheim.scoreboardfx.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public abstract class Period {

  private final String description;

  private final Duration start;

  private final Duration duration;

  private final boolean trackPenalties;

  abstract void onInit(StopWatch stopWatch);

  abstract void onComplete(StopWatch stopWatch);

  public Duration getEndTime() {
    return start.plus(duration);
  }

}
