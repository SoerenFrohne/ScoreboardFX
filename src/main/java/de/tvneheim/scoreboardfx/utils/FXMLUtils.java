package de.tvneheim.scoreboardfx.utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public final class FXMLUtils {

  public static void loadXml(Object root, String path) {
    FXMLLoader fxmlLoader = new FXMLLoader(FXMLUtils.class.getResource(path));
    fxmlLoader.setRoot(root);
    fxmlLoader.setController(root);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}
