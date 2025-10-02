package de.tvneheim.scoreboardfx.infrastructure.logging;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LoggingHelper {

  public static Path getLogDir(String appName) {
    String os = System.getProperty("os.name").toLowerCase();
    String userHome = System.getProperty("user.home");

    Path logPath;

    if (os.contains("win")) {
      logPath = Paths.get(System.getenv("LOCALAPPDATA"), appName, "logs");
    } else if (os.contains("mac")) {
      logPath = Paths.get(userHome, "Library", "Application Support", appName, "logs");
    } else {
      logPath = Paths.get(userHome, ".local", "share", appName, "logs");
    }

    try {
      Files.createDirectories(logPath);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return logPath;
  }
}
