package de.tvneheim.scoreboardfx.utils;

import javafx.scene.image.ImageView;

public final class LayoutUtils {

  public static void centerImage(ImageView imageView) {
    var img = imageView.getImage();
    if (img != null) {
      double w = 0;
      double h = 0;

      double ratioX = imageView.getFitWidth() / img.getWidth();
      double ratioY = imageView.getFitHeight() / img.getHeight();

      double reducCoeff = Math.min(ratioX, ratioY);

      w = img.getWidth() * reducCoeff;
      h = img.getHeight() * reducCoeff;

      imageView.setTranslateX((imageView.getFitWidth() - w) / 2);
      imageView.setTranslateY((imageView.getFitHeight() - h) / 2);

    }
  }
}
