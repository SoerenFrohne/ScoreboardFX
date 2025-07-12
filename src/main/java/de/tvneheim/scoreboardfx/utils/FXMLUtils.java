package de.tvneheim.scoreboardfx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

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

  public static void removeFromParent(Node nodeToRemove) {
    if (nodeToRemove.getParent() != null) {
      ((Pane) nodeToRemove.getParent()).getChildren().remove(nodeToRemove);
    }
  }

}
