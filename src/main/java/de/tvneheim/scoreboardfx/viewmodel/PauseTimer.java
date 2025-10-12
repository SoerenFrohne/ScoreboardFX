package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.Duration;

public record PauseTimer(
    DurationProperty current,
    DurationProperty length,
    BooleanProperty running,
    BooleanProperty finished
) implements TickListener {

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
  public void onTick(Duration delta) {
    if (current.isEqualOrLongerThan(length)) {
      finished.setValue(true);
      running.setValue(false);
    } else if (running.get()) {
      current.add(delta);
    }
  }

  public void reset() {
    this.current.setValue(Duration.ZERO);
    this.running.setValue(false);
    this.finished.setValue(false);
  }
}
