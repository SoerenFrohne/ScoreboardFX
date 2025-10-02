package de.tvneheim.scoreboardfx.utils;

import javafx.beans.property.ObjectPropertyBase;


import java.time.Duration;

public class DurationProperty extends ObjectPropertyBase<Duration> {

  public DurationProperty() {
    super(Duration.ZERO);
  }

  public DurationProperty(Duration initialValue) {
    super(initialValue);
  }

  @Override
  public Object getBean() {
    return null;
  }

  @Override
  public String getName() {
    return "duration";
  }

  // --- Arithmetische Hilfsmethoden ---

  public void add(Duration d) {
    set(get().plus(d));
  }

  public void subtract(Duration d) {
    set(get().minus(d));
  }

  public void plusMillis(long millis) {
    set(get().plusMillis(millis));
  }

  public void minusMillis(long millis) {
    set(get().minusMillis(millis));
  }

  public void plusSeconds(long seconds) {
    set(get().plusSeconds(seconds));
  }

  public void minusSeconds(long seconds) {
    set(get().minusSeconds(seconds));
  }

  public void plusMinutes(long minutes) {
    set(get().plusMinutes(minutes));
  }

  public void minusMinutes(long minutes) {
    set(get().minusMinutes(minutes));
  }

  public void reset() {
    set(Duration.ZERO);
  }
}