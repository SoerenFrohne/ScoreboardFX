package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public record PeriodTime(
    StringProperty period,
    DurationProperty endingTime,
    DurationProperty gameTime,
    BooleanProperty stopped,
    BooleanProperty finished
) implements Timer {

  public PeriodTime(Duration periodLength) {
    this(
        new SimpleStringProperty("1. Halbzeit"),
        new DurationProperty(periodLength),
        new DurationProperty(Duration.ZERO),
        new SimpleBooleanProperty(true),
        new SimpleBooleanProperty(false)
    );
  }

  public void start() {
    stopped.setValue(false);
  }

  public void stop() {
    stopped.setValue(true);
  }

  public void reset(Duration gameTime, Duration endingTime) {
    stop();
    this.gameTime.setValue(gameTime);
    this.endingTime.setValue(endingTime);
  }

  @Override
  public void update(long millis) {
    if (isRunning()) {
      gameTime.add(Duration.ofMillis(millis));

      if (isExpired()) {
        stop();
        finished.setValue(true);
      }
    }

  }

  private boolean isExpired() {
    var diff = endingTime.get().minus(getGameTime());
    return diff.isNegative() || diff.isZero();
  }

  public Duration getGameTime() {
    return gameTime.get();
  }


  public boolean isStopped() {
    return stopped.get();
  }

  public boolean isRunning() {
    return stopped.not().get();
  }

}
