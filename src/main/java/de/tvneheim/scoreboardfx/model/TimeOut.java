package de.tvneheim.scoreboardfx.model;

import java.time.Duration;

public record TimeOut(
    Duration start,
    Duration duration,
    Duration warning
) {
}
