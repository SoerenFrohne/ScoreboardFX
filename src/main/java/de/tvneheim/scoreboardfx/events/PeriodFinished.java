package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Status;

public class PeriodFinished extends Event {

  @Override
  public String description() {
    return "Ende der Halbzeit";
  }

  @Override
  public Game apply(Game current) {
    var timePerPeriod = current.settings().timePerPeriod();
    var currentPeriod = current.time().period();
    var time = current.time()
        .withPaused(true)
        .withMillis(0)
        .withSeconds(0)
        .withMinutes(currentPeriod * timePerPeriod)
        .withPeriod(currentPeriod + 1);
    return current
        .withStatus(Status.PAUSED)
        .withTime(time);
  }
}
