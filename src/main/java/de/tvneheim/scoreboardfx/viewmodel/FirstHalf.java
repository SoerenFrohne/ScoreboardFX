package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import lombok.extern.java.Log;

import java.time.Duration;

@Log
public class FirstHalf extends Period {

  public FirstHalf(Duration duration) {
    super("1. Halbzeit", Duration.ZERO, duration, true);
  }

  @Override
  public void onInit(StopWatch stopWatch) {
    stopWatch.pause();
  }

  @Override
  public void onComplete(StopWatch stopWatch) {
    log.info("First half finished!");
    SoundBoard.honkLong();
  }
}
