package de.tvneheim.scoreboardfx.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Collections;

public final class LayoutUtils {

  public static void setExactWidth(Region node, double width) {
    node.setMinWidth(width);
    node.setMaxWidth(width);
    node.setPrefWidth(width);
  }

  public static void bindExactWidth(Region node, DoubleBinding width) {
    node.minWidthProperty().bind(width);
    node.maxWidthProperty().bind(width);
    node.prefWidthProperty().bind(width);
  }

  public static void bindExactHeight(Region node, DoubleBinding height) {
    node.minHeightProperty().bind(height);
    node.maxHeightProperty().bind(height);
    node.prefHeightProperty().bind(height);
  }

  public static void bindExactSize(Region node, DoubleBinding width, DoubleBinding height) {
    bindExactHeight(node, height);
    bindExactWidth(node, width);
  }

  public static void centerImage(ImageView imageView) {
    var img = imageView.getImage();
    if (img != null) {

      double ratioX = imageView.getFitWidth() / img.getWidth();
      double ratioY = imageView.getFitHeight() / img.getHeight();

      double reducCoeff = Math.min(ratioX, ratioY);

      double w = img.getWidth() * reducCoeff;
      double h = img.getHeight() * reducCoeff;

      imageView.setTranslateX((imageView.getFitWidth() - w) / 2);
      imageView.setTranslateY((imageView.getFitHeight() - h) / 2);

    }
  }

  public static void mirrorLayout(Node node) {

    if (node instanceof Parent parent) {
      var children = new ArrayList<>(parent.getChildrenUnmodifiable());

      for (Node child : children) {
        mirrorLayout(child);
      }
    }

    if (node instanceof HBox hBox) {
      var reversed = new ArrayList<>(hBox.getChildren());

      Collections.reverse(reversed);
      hBox.getChildren().setAll(reversed);
    }
  }

  public static Node getTopLevelRoot(Node node) {
    var current = node;

    while (current.getParent() != null) {
      current = current.getParent();
    }

    return current;
  }
}
