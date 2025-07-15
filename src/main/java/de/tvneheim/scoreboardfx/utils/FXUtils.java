package de.tvneheim.scoreboardfx.utils;

import java.time.Duration;

public final class FXUtils {

  public static javafx.util.Duration convertToFxDuration(Duration duration) {
    return new javafx.util.Duration(duration.toMillis());
  }
}
