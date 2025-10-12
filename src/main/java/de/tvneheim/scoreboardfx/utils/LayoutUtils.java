package de.tvneheim.scoreboardfx.utils;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

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
}
