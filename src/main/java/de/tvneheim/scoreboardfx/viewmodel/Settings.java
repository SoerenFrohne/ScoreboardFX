package de.tvneheim.scoreboardfx.viewmodel;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import lombok.Builder;

import java.time.Duration;

@Builder
public record Settings(

    // Teams
    StringProperty homeTeamName,
    ObjectProperty<Image> homeTeamLogo,
    StringProperty guestTeamName,
    ObjectProperty<Image> guestTeamLogo,

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
        .homeTeamName(new SimpleStringProperty("Heim"))
        .homeTeamLogo(new SimpleObjectProperty<>())
        .guestTeamName(new SimpleStringProperty("Gast"))
        .guestTeamLogo(new SimpleObjectProperty<>())

        // Time
        .numberOfPeriods(new SimpleIntegerProperty(2))
        .lengthPerPeriod(new SimpleObjectProperty<>(Duration.ofSeconds(15))) //TODO: Reset to 30 minutes
        .pauseBetweenPeriods(new SimpleObjectProperty<>(Duration.ofSeconds(10)))

        // Time-Outs
        .timePerTeamTimeOut(new SimpleObjectProperty<>(Duration.ofSeconds(10)))
        .timeOutWarningTime(new SimpleObjectProperty<>(Duration.ofSeconds(5)))
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
