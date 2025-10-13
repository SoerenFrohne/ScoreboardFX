package de.tvneheim.scoreboardfx.utils;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.time.Duration;

/**
 * Eine SpinnerValueFactory für JavaFX, die Duration-Werte von 0 bis 90 Minuten
 * in 30-Sekunden-Schritten verwaltet und im Format mm:ss anzeigt.
 */
public class DurationSpinnerValueFactory extends SpinnerValueFactory<Duration> {

  private final Duration step;
  private final Duration min;
  private final Duration max;

  public DurationSpinnerValueFactory() {
    this(Duration.ZERO, Duration.ofMinutes(90), Duration.ofSeconds(30));
  }

  public DurationSpinnerValueFactory(Duration min, Duration max, Duration step) {
    this.min = min;
    this.max = max;
    this.step = step;

    setValue(min);

    // StringConverter für Anzeige und Parsing
    setConverter(new StringConverter<>() {
      @Override
      public String toString(Duration duration) {
        return FormatterUtils.time(duration);
      }

      @Override
      public Duration fromString(String string) {
        try {
          String[] parts = string.split(":");
          long minutes = Long.parseLong(parts[0]);
          long seconds = Long.parseLong(parts[1]);
          Duration d = Duration.ofMinutes(minutes).plusSeconds(seconds);

          if (d.compareTo(max) > 0) d = max;
          if (d.compareTo(min) < 0) d = min;

          return d;
        } catch (Exception e) {
          // Falls ungültige Eingabe → alten Wert behalten
          return getValue();
        }
      }
    });
  }

  @Override
  public void decrement(int steps) {
    Duration newValue = getValue().minus(step.multipliedBy(steps));
    if (newValue.compareTo(min) < 0) newValue = min;
    setValue(newValue);
  }

  @Override
  public void increment(int steps) {
    Duration newValue = getValue().plus(step.multipliedBy(steps));
    if (newValue.compareTo(max) > 0) newValue = max;
    setValue(newValue);
  }
}
