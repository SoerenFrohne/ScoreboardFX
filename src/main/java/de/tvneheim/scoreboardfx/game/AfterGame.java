package de.tvneheim.scoreboardfx.game;

import java.time.Duration;

public class AfterGame extends Period {

  public AfterGame() {
    super("Ende", Duration.ZERO, Duration.ZERO, false);
  }

  @Override
  public void onInit(StopWatch stopWatch) {
    // not necessary
  }

  @Override
  public void onComplete(StopWatch stopWatch) {
    // not necessary
  }
}
