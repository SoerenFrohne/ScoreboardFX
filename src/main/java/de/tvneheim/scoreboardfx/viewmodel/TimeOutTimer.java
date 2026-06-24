package de.tvneheim.scoreboardfx.viewmodel;

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
) implements TickListener {

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
    current.setValue(Duration.ZERO);
    running.setValue(true);
  }

  public void skip() {
    running.setValue(false);
  }

  @Override
  public void onTick(Duration delta) {

    if (running.get()) {
      current.add(delta);
    }

    if (current.isEqualOrLongerThan(warningTime)) {
      overWarningTime.setValue(true);
    }

    if (current.isEqualOrLongerThan(length)) {
      running.setValue(false);
    }
  }
}
