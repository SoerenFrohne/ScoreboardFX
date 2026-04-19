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
    ObjectProperty<Duration> penaltyLength,

    // Time-Outs
    ObjectProperty<Duration> timePerTeamTimeOut,
    ObjectProperty<Duration> timeOutWarningTime,
    IntegerProperty maxTimeOutsPerPeriod,
    IntegerProperty maxTimeOutsPerGame,

    // Advertisement
    ObjectProperty<Duration> showTimeOfAds,

    // Data
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
        .lengthPerPeriod(new SimpleObjectProperty<>(Duration.ofMinutes(30)))
        .pauseBetweenPeriods(new SimpleObjectProperty<>(Duration.ofMinutes(10)))
        .penaltyLength(new SimpleObjectProperty<>(Duration.ofMinutes(2)))

        // Time-Outs
        .timePerTeamTimeOut(new SimpleObjectProperty<>(Duration.ofSeconds(60)))
        .timeOutWarningTime(new SimpleObjectProperty<>(Duration.ofSeconds(50)))
        .maxTimeOutsPerPeriod(new SimpleIntegerProperty(2))
        .maxTimeOutsPerGame(new SimpleIntegerProperty(3))

        // Advertisement
        .showTimeOfAds(new SimpleObjectProperty<>(Duration.ofSeconds(30)))

        // Data
        .pathToAdImages(new SimpleStringProperty())
        .pathToAdVideos(new SimpleStringProperty())
        ;
  }

}
