package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Duration;

public record TimeOutTimer(
    BooleanProperty running,
    DurationProperty current,
    DurationProperty length,
    DurationProperty warningTime,
    BooleanProperty overWarningTime
) implements Timer {

  public TimeOutTimer(Duration length, Duration warningTime) {
    this(
        new SimpleBooleanProperty(false),
        new DurationProperty(),
        new DurationProperty(length),
        new DurationProperty(warningTime),
        new SimpleBooleanProperty(false)
    );
  }

  public void start() {
    running.setValue(true);
  }

  @Override
  public void update(long millis) {

    if (running.get()) {
      current.plusMillis(millis);
    }

    if (current.isEqualOrLongerThan(warningTime)) {
      overWarningTime.setValue(true);
    }

    if (current.isEqualOrLongerThan(length)) {
      running.setValue(false);
    }
  }
}
