package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;

import java.time.Duration;

public class SecondHalf extends Period {

  public SecondHalf(Duration duration) {
    super("2. Halbzeit", duration, duration, true);
  }

  @Override
  public void onInit(StopWatch stopWatch) {
    stopWatch.pause();
  }

  @Override
  public void onComplete(StopWatch stopWatch) {
    SoundBoard.honkLong();
    stopWatch.pause();
  }
}
