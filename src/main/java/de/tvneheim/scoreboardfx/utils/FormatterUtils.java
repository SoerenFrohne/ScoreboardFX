package de.tvneheim.scoreboardfx.utils;

public final class FormatterUtils {

  public static String time(int minutes, int seconds) {
    return String.format("%02d:%02d", minutes, seconds);
  }
}
