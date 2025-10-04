package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.*;

import java.time.Duration;

public record SuspensionTimer(
    IntegerProperty number,
    DurationProperty remainingTime,
    BooleanProperty completed
) implements Timer {

  public SuspensionTimer(int number, Duration duration) {
    this(
        new SimpleIntegerProperty(number),
        new DurationProperty(duration),
        new SimpleBooleanProperty(false)
    );
  }

  @Override
  public void update(long millis) {
    remainingTime.minusMillis(millis);

    if (remainingTime.get().isZero() || remainingTime.get().isNegative()) {
      completed.setValue(true);
    }
  }
}
