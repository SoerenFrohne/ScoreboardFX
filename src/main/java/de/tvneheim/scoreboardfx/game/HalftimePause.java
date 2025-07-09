package de.tvneheim.scoreboardfx.game;

import lombok.extern.java.Log;

import java.time.Duration;

@Log
public class HalftimePause extends Period {

  public HalftimePause(Duration duration) {
    super("Pause", Duration.ZERO, duration, false);
  }

  @Override
  public void onInit(StopWatch stopWatch) {
    stopWatch.reset();
    stopWatch.start();
    log.info("Pause started...");
  }

  @Override
  public void onComplete(StopWatch stopWatch) {
    stopWatch.pause();
  }
}
