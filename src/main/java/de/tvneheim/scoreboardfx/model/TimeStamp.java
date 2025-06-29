package de.tvneheim.scoreboardfx.model;

import java.time.Duration;

public record TimeStamp(
    int millis,
    int seconds,
    int minutes
) {

  public TimeStamp fromTime(Time time) {
    return new TimeStamp(time.millis(), time.seconds(), time.minutes());
  }

  public static Duration difference(TimeStamp start, TimeStamp next) {
    Duration durationStart = Duration.ofSeconds(start.seconds()).plus(Duration.ofMinutes(start.minutes()));
    Duration durationNext = Duration.ofSeconds(next.seconds()).plus(Duration.ofMinutes(next.minutes()));
    return durationNext.minus(durationStart);
  }

}
