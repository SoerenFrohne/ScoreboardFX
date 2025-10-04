package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Duration;

public record PauseTimer(
    DurationProperty current,
    DurationProperty length,
    BooleanProperty running,
    BooleanProperty finished
) implements Timer {

  public PauseTimer(Duration length) {
    this(
        new DurationProperty(),
        new DurationProperty(length),
        new SimpleBooleanProperty(false),
        new SimpleBooleanProperty(false)
    );
  }

  public void start() {
    running.setValue(true);
  }

  @Override
  public void update(long millis) {
    if (current.isEqualOrLongerThan(length)) {
      finished.setValue(true);
      running.setValue(false);
    } else if (running.get()) {
      current.plusMillis(millis);
    }
  }

  public void reset() {
    this.current.setValue(Duration.ZERO);
    this.running.setValue(false);
    this.finished.setValue(false);
  }
}
