package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.utils.DurationProperty;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import lombok.Builder;

import java.nio.file.Paths;
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
    DurationProperty lengthPerPeriod,
    DurationProperty pauseBetweenPeriods,
    DurationProperty penaltyLength,

    // Time-Outs
    DurationProperty timePerTeamTimeOut,
    DurationProperty timeOutWarningTime,
    IntegerProperty maxTimeOutsPerPeriod,
    IntegerProperty maxTimeOutsPerGame,

    // Advertisement
    DurationProperty showTimeOfAds,

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
        .lengthPerPeriod(new DurationProperty(Duration.ofMinutes(30)))
        .pauseBetweenPeriods(new DurationProperty(Duration.ofMinutes(10)))
        .penaltyLength(new DurationProperty(Duration.ofMinutes(2)))

        // Time-Outs
        .timePerTeamTimeOut(new DurationProperty(Duration.ofSeconds(60)))
        .timeOutWarningTime(new DurationProperty(Duration.ofSeconds(50)))
        .maxTimeOutsPerPeriod(new SimpleIntegerProperty(2))
        .maxTimeOutsPerGame(new SimpleIntegerProperty(3))

        // Advertisement
        .showTimeOfAds(new DurationProperty(Duration.ofSeconds(10)))

        // Data
        .pathToAdImages(new SimpleStringProperty(Paths.get("predefinitions/ads").toAbsolutePath().normalize().toString()))
        .pathToAdVideos(new SimpleStringProperty())
        ;
  }

}
