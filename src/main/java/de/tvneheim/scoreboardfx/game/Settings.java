package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.infrastructure.settings.PredefinedTeam;
import javafx.beans.property.*;

public record Settings(
    ObjectProperty<PredefinedTeam> homeTeam,
    ObjectProperty<PredefinedTeam> guestTeam,
    IntegerProperty numberOfPeriods,
    IntegerProperty minutesPerPeriod,
    IntegerProperty secondsPerPeriod,
    IntegerProperty minutesBetweenPeriods,
    IntegerProperty secondsBetweenPeriods,
    IntegerProperty secondsBetweenAds,
    StringProperty pathToLogos,
    StringProperty pathToAdImages,
    StringProperty pathToAdVideos
) {

  public Settings() {
    this(
        new SimpleObjectProperty<>(new PredefinedTeam("HEIM", null, "HEIM")),
        new SimpleObjectProperty<>(new PredefinedTeam("GAST", null, "GAST")),
        new SimpleIntegerProperty(2),
        new SimpleIntegerProperty(0),
        new SimpleIntegerProperty(30),
        new SimpleIntegerProperty(0),
        new SimpleIntegerProperty(10),
        new SimpleIntegerProperty(5),
        new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Logos"),
        new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Werbung\\Bilder"),
        new SimpleStringProperty("")
    );
  }
}
