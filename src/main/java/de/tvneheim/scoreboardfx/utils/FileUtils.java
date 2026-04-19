package de.tvneheim.scoreboardfx.utils;


import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class FileUtils {

  private FileUtils() {
  }

  public static List<File> listFiles(String path) {
    if (path == null) {
      return List.of();
    }

    var directory = new File(path);
    var files = directory.listFiles();
    if (files == null) {
      return List.of();
    } else {
      return Arrays.stream(files).toList();
    }
  }
}
