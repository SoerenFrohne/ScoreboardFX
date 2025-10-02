package de.tvneheim.scoreboardfx.game;


import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class StopWatch extends AnimationTimer {

  // Game loop stats
  private long lastTick = -1L;

  // settings
  private final Settings settings;

  // time stats
  private final PeriodTime periodTime;
  private final SuspensionSlots suspensionsHome, suspensionsGuest;
  private final TimeOutSlots timeOutsHome, timeOutsGuest;

  public StopWatch(Settings settings) {
    this.settings = settings;
    this.periodTime = new PeriodTime(settings.lengthPerPeriod().get());
    this.suspensionsHome = new SuspensionSlots();
    this.suspensionsGuest = new SuspensionSlots();
    this.timeOutsHome = new TimeOutSlots(settings.maxTimeOutsPerPeriod().get(), settings.maxTimeOutsPerGame().get());
    this.timeOutsGuest = new TimeOutSlots(settings.maxTimeOutsPerPeriod().get(), settings.maxTimeOutsPerGame().get());

    // Start the animation timer loop
    this.start();
  }

  @Override
  public void handle(long now) {
    if (lastTick < 0) {
      lastTick = now; // Initialisierung beim ersten Frame
    }

    var deltaMillis = TimeUnit.NANOSECONDS.toMillis(now - lastTick);
    lastTick = now;

    periodTime.update(deltaMillis);
  }

  public void play() {
    periodTime.start();
  }

  public void pause() {
    periodTime.stop();
  }

}
