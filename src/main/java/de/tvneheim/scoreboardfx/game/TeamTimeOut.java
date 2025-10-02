package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.model.TimeOut;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;

public record TeamTimeOut(
    ObjectProperty<Duration> start,
    ObjectProperty<Duration> duration,
    ObjectProperty<Duration> warningTime,
    ObjectProperty<Duration> remainingTime,
    BooleanProperty completed
) {

  public TeamTimeOut(Duration start, Duration duration, Duration warningTime, Duration remainingTime, boolean completed) {
    this(
        new SimpleObjectProperty<>(start),
        new SimpleObjectProperty<>(duration),
        new SimpleObjectProperty<>(warningTime),
        new SimpleObjectProperty<>(remainingTime),
        new SimpleBooleanProperty(completed)
    );
  }

  public TeamTimeOut(TimeOut timeOut) {
    this(timeOut.start(), timeOut.duration(), timeOut.warning(), timeOut.duration(), false);
  }

  public void updateTime(Duration current) {
    var passed = this.start.getValue().minus(current);
    if (passed.isNegative()) {
      remainingTime.setValue(Duration.ZERO);
    } else {
      var newValue = this.duration.getValue().minus(passed);
      if (newValue.isZero() || newValue.isNegative()) {
        //log.info("Penalty completed: {}", this);
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
