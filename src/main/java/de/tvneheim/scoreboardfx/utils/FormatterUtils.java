package de.tvneheim.scoreboardfx.utils;

public final class FormatterUtils {

  public static String time(int minutes, int seconds) {
    return String.format("%02d:%02d", minutes, seconds);
  }

  public static String doubleDigits(int value) {
    return String.format("%02d", value);
  }
}
