package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.model.Penalty;
import javafx.beans.property.*;
import lombok.extern.java.Log;

import java.time.Duration;

@Log
public record Suspension(
    IntegerProperty number,
    ObjectProperty<Duration> start,
    ObjectProperty<Duration> duration,
    ObjectProperty<Duration> remainingTime,
    BooleanProperty completed
) {

  private Suspension(int number, Duration start, Duration duration, Duration remainingTime, boolean suspensionCompleted) {
    this(
        new SimpleIntegerProperty(number),
        new SimpleObjectProperty<>(start),
        new SimpleObjectProperty<>(duration),
        new SimpleObjectProperty<>(remainingTime),
        new SimpleBooleanProperty(suspensionCompleted)
    );
  }

  public Suspension(Penalty penalty) {
    this(
        penalty.player().number(),
        penalty.start(),
        penalty.duration(),
        penalty.duration(),
        penalty.suspensionCompleted()
    );
  }

  public void updateTime(Duration current) {
    var passed = current.minus(start.getValue());
    if (passed.isNegative()) {
      remainingTime.setValue(Duration.ZERO);
    } else {
      var newValue = this.duration.getValue().minus(passed);
      if (newValue.isZero() || newValue.isNegative()) {
        log.info("Penalty completed: " + this);
        remainingTime.setValue(Duration.ZERO);
        completed.setValue(true);
      } else {
        remainingTime.setValue(newValue);
      }
    }
  }

  public boolean isRunning() {
    return !completed.getValue();
  }
}
