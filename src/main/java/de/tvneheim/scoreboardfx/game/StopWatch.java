package de.tvneheim.scoreboardfx.game;


import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class StopWatch extends AnimationTimer {

  private int period = 1;
  private long lastTick = -1L;

  // settings
  private final Settings settings;

  // time stats
  private final PeriodTimer periodTimer;
  private final PauseTimer pauseTimer;
  private final TimeOutTimer timeOutTimer;
  private final SuspensionSlots suspensionsHome, suspensionsGuest;

  public StopWatch(Settings settings) {
    this.settings = settings;
    this.periodTimer = new PeriodTimer(settings.lengthPerPeriod().get());
    this.pauseTimer = new PauseTimer(settings.pauseBetweenPeriods().get());
    this.timeOutTimer = new TimeOutTimer(settings.timePerTeamTimeOut().get(), settings.timeOutWarningTime().get());
    this.suspensionsHome = new SuspensionSlots();
    this.suspensionsGuest = new SuspensionSlots();

    // set up period management
    periodTimer.finished().addListener((observableValue, aBoolean, finished) -> {
      if (finished) {
        pauseTimer.start();
      }
    });
    pauseTimer.finished().addListener((observable, oldValue, finished) -> {
      if (finished) {
        period++;
        periodTimer.reset("2. Halbzeit", periodTimer.endingTime().get().multipliedBy(period), periodTimer.currentTime().get());
        pauseTimer.reset();
      }
    });

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

    periodTimer.update(deltaMillis);

    if (periodTimer.isRunning()) {
      suspensionsGuest.getSuspensions().forEach(suspensionTimer -> suspensionTimer.update(deltaMillis));
      suspensionsHome.getSuspensions().forEach(suspensionTimer -> suspensionTimer.update(deltaMillis));
    }

    pauseTimer.update(deltaMillis);
    timeOutTimer.update(deltaMillis);
  }

  public void play() {
    periodTimer.start();
  }

  public void pause() {
    periodTimer.stop();
  }

}
