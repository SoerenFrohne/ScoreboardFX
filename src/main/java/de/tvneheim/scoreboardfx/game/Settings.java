package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.infrastructure.settings.PredefinedTeam;
import javafx.beans.property.*;
import lombok.Builder;

import java.time.Duration;

@Builder
public record Settings(

    // Teams
    ObjectProperty<PredefinedTeam> homeTeam,
    ObjectProperty<PredefinedTeam> guestTeam,

    // Time Management
    IntegerProperty numberOfPeriods,
    ObjectProperty<Duration> lengthPerPeriod,
    ObjectProperty<Duration> pauseBetweenPeriods,

    // Time-Outs
    ObjectProperty<Duration> timePerTeamTimeOut,
    ObjectProperty<Duration> timeOutWarningTime,
    IntegerProperty maxTimeOutsPerPeriod,
    IntegerProperty maxTimeOutsPerGame,

    // Advertisement
    ObjectProperty<Duration> showTimeOfAds,

    // Data
    StringProperty pathToLogos,
    StringProperty pathToAdImages,
    StringProperty pathToAdVideos
) {

  public static SettingsBuilder defaultSettings() {
    return Settings.builder()
        // Teams
        .homeTeam(new SimpleObjectProperty<>(new PredefinedTeam("HEIM", null, "HEIM")))
        .guestTeam(new SimpleObjectProperty<>(new PredefinedTeam("GAST", null, "GAST")))

        // Time
        .numberOfPeriods(new SimpleIntegerProperty(2))
        .lengthPerPeriod(new SimpleObjectProperty<>(Duration.ofMinutes(30)))
        .pauseBetweenPeriods(new SimpleObjectProperty<>(Duration.ofMinutes(10)))

        // Time-Outs
        .timePerTeamTimeOut(new SimpleObjectProperty<>(Duration.ofMinutes(1)))
        .timeOutWarningTime(new SimpleObjectProperty<>(Duration.ofSeconds(50)))
        .maxTimeOutsPerPeriod(new SimpleIntegerProperty(2))
        .maxTimeOutsPerGame(new SimpleIntegerProperty(3))

        // Advertisement
        .showTimeOfAds(new SimpleObjectProperty<>(Duration.ofSeconds(30)))

        // Data
        .pathToLogos(new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Logos"))
        .pathToAdImages(new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Werbung\\Bilder"))
        .pathToAdVideos(new SimpleStringProperty("D:\\TV Neheim 1884\\Scoreboard\\Werbung\\Videos"))
        ;
  }

}
