package de.tvneheim.scoreboardfx.model;

public record Settings(
    int timePerPeriod,
    int secondsBetweenAds,
    String pathToAdLogos,
    String pathToAdImages,
    String pathToAdVideos
) {
}
