package de.tvneheim.scoreboardfx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public record Settings(
    IntegerProperty timePerPeriod,
    IntegerProperty pauseBetweenPeriods,
    IntegerProperty secondsBetweenAds,
    StringProperty pathToLogos,
    StringProperty pathToAdImages,
    StringProperty pathToAdVideos
) {

  public Settings() {
    this(
        new SimpleIntegerProperty(30),
        new SimpleIntegerProperty(10),
        new SimpleIntegerProperty(5),
        new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Logos"),
        new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Werbung\\Bilder"),
        new SimpleStringProperty("")
    );
  }
}
