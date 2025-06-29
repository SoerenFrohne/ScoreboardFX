package de.tvneheim.scoreboardfx.model;


public record Penalties(
    Time yellow,
    Time firstPenalty,
    Time secondPenalty,
    Time thirdPenalty,
    Time red,
    Time blue
) {
}
