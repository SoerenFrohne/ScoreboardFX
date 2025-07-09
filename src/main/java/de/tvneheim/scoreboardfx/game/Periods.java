package de.tvneheim.scoreboardfx.game;

import java.time.Duration;
import java.util.List;

public final class Periods {

  //TODO: from settings

  public static List<Period> standardGame(int minutesPerPeriod, int minutesPerPause) {
    return List.of(
        new FirstHalf(Duration.ofMinutes(minutesPerPeriod)),
        new HalftimePause(Duration.ofMinutes(minutesPerPause)),
        new SecondHalf(Duration.ofMinutes(minutesPerPeriod)),
        new AfterGame()
    );
  }

  public static List<Period> standardGame(Duration periodDuration, Duration pauseDuration) {
    return List.of(
        new FirstHalf(periodDuration),
        new HalftimePause(pauseDuration),
        new SecondHalf(periodDuration),
        new AfterGame()
    );
  }
}
