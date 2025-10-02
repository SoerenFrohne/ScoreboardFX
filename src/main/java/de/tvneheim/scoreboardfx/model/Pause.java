package de.tvneheim.scoreboardfx.model;


import java.time.Duration;

public record Pause(
    boolean active,
    Duration duration
) {

}
