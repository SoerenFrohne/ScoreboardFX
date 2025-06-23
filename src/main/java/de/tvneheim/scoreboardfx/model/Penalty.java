package de.tvneheim.scoreboardfx.model;

public record Penalty(
        Player player,
        Time start,
        Time duration
) {
}
