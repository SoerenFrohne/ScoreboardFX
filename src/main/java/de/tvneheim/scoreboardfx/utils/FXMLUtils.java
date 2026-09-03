package de.tvneheim.scoreboardfx.utils;

import de.tvneheim.scoreboardfx.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

  public static Stage initStage(String fxmlPath, String stylesheetPath, String title) throws IOException {
    var viewLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
    var viewScene = new Scene(viewLoader.load());
    viewScene.getStylesheets().add(MainApplication.class.getResource(stylesheetPath).toExternalForm());
    var stage = new Stage();
    stage.setTitle("Scoreboard View");
    stage.setScene(viewScene);

    return stage;
  }

}
