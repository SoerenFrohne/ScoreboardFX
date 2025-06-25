package de.tvneheim.scoreboardfx.model;

public record Settings(
    int timePerPeriod,
    String pathToAdLogos,
    String pathToAdImages,
    String pathToAdVideos
) {
}
