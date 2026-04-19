package de.tvneheim.scoreboardfx.viewmodel;


import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class StopWatch extends AnimationTimer {

  private static final Duration TICK_DELTA = Duration.ofSeconds(1);
  private long lastTickSecond = -1;
  private long startNanos = -1L;

  // settings
  private final Settings settings;

  // time stats
  private IntegerProperty period = new SimpleIntegerProperty(1);
  private final PeriodTimer periodTimer;
  private final ObjectProperty<GameTimeStatus> gameTimeStatus = new SimpleObjectProperty<>(GameTimeStatus.PRE_GAME);
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
      log.info("Halbzeitende : {}", periodTimer.currentTime().get());

      if (finished) {
        if (period.get() + 1 > settings.numberOfPeriods().get()) {
          gameTimeStatus.setValue(GameTimeStatus.FINISHED);
          log.info("Spielende : {}", periodTimer.currentTime().get());
        } else {
          SoundBoard.honkLong();
          pauseTimer.start();
        }
      }
    });

    pauseTimer.finished().addListener((observable, oldValue, finished) -> {
      if (finished) {
        skipToNextPeriod();
      }
    });

    // Start the animation timer loop
    this.start();
  }

  public void skipToNextPeriod() {
    period.setValue(period.get() + 1);
    periodTimer.reset(period.get() + ". Halbzeit", periodTimer.endingTime().get().multipliedBy(period.get()), periodTimer.currentTime().get());
    pauseTimer.reset();
    log.info("Halbzeit {} konfiguriert. Aktuelle Zeit ist {}", period.get(), periodTimer.currentTime().get());

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
    gameTimeStatus.setValue(GameTimeStatus.RUNNING);
    periodTimer.start();
  }

  public void pause() {
    gameTimeStatus.setValue(GameTimeStatus.PAUSED);
    periodTimer.stop();
  }

}
