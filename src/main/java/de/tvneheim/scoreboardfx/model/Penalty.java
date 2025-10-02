package de.tvneheim.scoreboardfx.model;

import lombok.With;

import java.time.Duration;

@With
public record Penalty(
    PenaltyType type,
    Player player,
    Duration start,
    Duration duration,
    boolean suspensionCompleted
) {


  public Penalty(PenaltyType penaltyType, Player player, Duration start, Duration duration) {
    this(penaltyType, player, start, duration, false);
  }

  public static Penalty empty(Player player, Duration timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty yellow(Player player, Duration timeStamp) {
    return new Penalty(PenaltyType.YELLOW, player, timeStamp, Duration.ZERO);
  }

  public static Penalty twoMinutes(Player player, Duration start) {
    return new Penalty(PenaltyType.TWO_MINUTES, player, start, Duration.ofSeconds(5)); //TODO: revert
  }

  public static Penalty red(Player player, Duration start) {
    return new Penalty(PenaltyType.RED, player, start, Duration.ofMinutes(2));
  }

  public static Penalty blue(Player player, Duration start) {
    return new Penalty(PenaltyType.BLUE, player, start, Duration.ofMinutes(2));
  }

  public static Penalty custom(Player player, Duration start, Duration duration) {
    return new Penalty(PenaltyType.BLUE, player, start, duration);
  }

  public static Penalty test(Player player, Duration start) {
    return new Penalty(PenaltyType.BLUE, player, start, Duration.ofSeconds(10));
  }

  public Duration getRemainingTime(Duration current) {
    var passed = current.minus(start);
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
