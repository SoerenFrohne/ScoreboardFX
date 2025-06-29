package de.tvneheim.scoreboardfx.model;

import java.time.Duration;

public record Penalty(
    PenaltyType type,
    Player player,
    TimeStamp start,
    Duration duration
) {

  public static Penalty empty(Player player, TimeStamp timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty yellow(Player player, TimeStamp timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty twoMinutes(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.TWO_MINUTES, player, start, Duration.ofMinutes(2));
  }

  public static Penalty red(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.RED, player, start, Duration.ofMinutes(2));
  }

  public static Penalty blue(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.BLUE, player, start, Duration.ofMinutes(2));
  }

  public Duration calculateRemainingTime(TimeStamp current) {
    var passed = TimeStamp.difference(this.start, current);
    if (passed.isNegative()) {
      return Duration.ZERO;
    } else {
      return this.duration.minus(passed);
    }
  }
}
