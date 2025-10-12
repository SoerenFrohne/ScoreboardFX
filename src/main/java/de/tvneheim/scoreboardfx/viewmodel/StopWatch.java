package de.tvneheim.scoreboardfx.viewmodel;


import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class StopWatch extends AnimationTimer {

  private static final Duration TICK_DELTA = Duration.ofSeconds(1);
  private int period = 1;
  private long lastTickSecond = -1;
  private long startNanos = -1L;

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
    if (startNanos < 0) {
      startNanos = now; // Initialisierung beim ersten Frame
    }

    var elapsedSeconds = TimeUnit.NANOSECONDS.toSeconds(now - startNanos);

    // Prüfen, ob wir in eine neue Sekunde übergegangen sind
    if (elapsedSeconds != lastTickSecond) {
      lastTickSecond = elapsedSeconds;

      periodTimer.onTick(TICK_DELTA);

      if (periodTimer.isRunning()) {
        suspensionsGuest.getSuspensions().forEach(suspensionTimer -> suspensionTimer.onTick(TICK_DELTA));
        suspensionsHome.getSuspensions().forEach(suspensionTimer -> suspensionTimer.onTick(TICK_DELTA));
      }

      pauseTimer.onTick(TICK_DELTA);
      timeOutTimer.onTick(TICK_DELTA);
    }
  }


  public void play() {
    periodTimer.start();
  }

  public void pause() {
    periodTimer.stop();
  }

}
