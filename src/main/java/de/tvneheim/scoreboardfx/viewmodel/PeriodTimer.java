package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public record PeriodTimer(
    StringProperty description,
    DurationProperty endingTime,
    DurationProperty currentTime,
    BooleanProperty stopped,
    BooleanProperty finished
) implements TickListener {

  public PeriodTimer(Duration periodLength) {
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

  @Override
  public void onTick(Duration delta) {
    if (isRunning()) {
      currentTime.add(delta);

      if (isExpired()) {
        log.info("Expired: {}", currentTime);
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
    return currentTime.get();
  }

  public boolean isStopped() {
    return stopped.get();
  }

  public boolean isRunning() {
    return stopped.not().get();
  }

  public void reset(String description, Duration length, Duration current) {
    this.description.setValue(description);
    this.endingTime.set(length);
    this.currentTime.set(current);
    this.finished.setValue(false);
  }
}
