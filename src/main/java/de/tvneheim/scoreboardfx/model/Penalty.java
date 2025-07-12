package de.tvneheim.scoreboardfx.model;

import lombok.With;

import java.time.Duration;

@With
public record Penalty(
    PenaltyType type,
    Player player,
    TimeStamp start,
    Duration duration,
    boolean suspensionCompleted
) {


  public Penalty(PenaltyType penaltyType, Player player, TimeStamp start, Duration duration) {
    this(penaltyType, player, start, duration, false);
  }

  public static Penalty empty(Player player, TimeStamp timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty yellow(Player player, TimeStamp timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty twoMinutes(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.TWO_MINUTES, player, start, Duration.ofSeconds(5)); //TODO: revert
  }

  public static Penalty red(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.RED, player, start, Duration.ofMinutes(2));
  }

  public static Penalty blue(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.BLUE, player, start, Duration.ofMinutes(2));
  }

  public static Penalty custom(Player player, TimeStamp start, Duration duration) {
    return new Penalty(PenaltyType.BLUE, player, start, duration);
  }

  public static Penalty test(Player player, TimeStamp start) {
    return new Penalty(PenaltyType.BLUE, player, start, Duration.ofSeconds(10));
  }

  public Duration getRemainingTime(TimeStamp current) {
    var passed = TimeStamp.difference(this.start, current);
    if (passed.isNegative()) {
      return Duration.ZERO;
    } else {
      var newValue = this.duration.minus(passed);
      if (newValue.isZero() || newValue.isNegative()) {
        return Duration.ZERO;
      } else {
       return newValue;
      }
    }
  }

  public boolean isRunning() {
    return !suspensionCompleted;
  }

}
