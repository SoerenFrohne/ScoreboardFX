package de.tvneheim.scoreboardfx.utils;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public final class LayoutUtils {

  public static void setExactWidth(Region node, double width) {
    node.setMinWidth(width);
    node.setMaxWidth(width);
    node.setPrefWidth(width);
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
