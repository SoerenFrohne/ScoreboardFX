package de.tvneheim.scoreboardfx.utils;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FormatterUtils {

  public static String time(int minutes, int seconds) {
    return String.format("%02d:%02d", minutes, seconds);
  }

  public static String time(Duration duration) {
    return String.format("%02d:%02d", duration.toMinutesPart(), duration.toSecondsPart());
  }

  public static StringBinding bindFormattedTime(ObjectProperty<Duration> duration) {
    return new StringBinding() {
      {
        bind(duration);
      }

      @Override
      protected String computeValue() {
        return time(duration.get());
      }
    };
  }

  public static String doubleDigits(int value) {
    return String.format("%02d", value);
  }

  public static String dateTime(LocalDateTime localDateTime) {
    return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) + " Uhr";
  }
}
