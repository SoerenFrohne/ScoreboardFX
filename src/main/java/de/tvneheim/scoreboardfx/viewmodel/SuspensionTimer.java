package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public record SuspensionTimer(
    IntegerProperty number,
    DurationProperty remainingTime,
    BooleanProperty completed
) implements TickListener {

  public SuspensionTimer(int number, Duration duration) {
    this(
        new SimpleIntegerProperty(number),
        new DurationProperty(duration),
        new SimpleBooleanProperty(false)
    );
  }

  @Override
  public void onTick(Duration delta) {
    remainingTime.subtract(delta);

    if (remainingTime.get().isZero() || remainingTime.get().isNegative()) {
      completed.setValue(true);
    }
  }

  public void setRemainingTime(int minutes, int seconds) {
    remainingTime.setValue(Duration.ofMinutes(minutes).plusSeconds(seconds));
  }
}
