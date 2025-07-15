package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.game.Period;
import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PeriodFinished extends Event {

  private final Period period;

  @Override
  public String description() {
    return "Ende der Halbzeit";
  }

  @Override
  public Game apply(Game current) {
    var timePerPeriod = period.getDuration();
    var currentPeriod = current.time().period();
    var time = current.time()
        .withPaused(true)
        .withMillis(currentPeriod * timePerPeriod.toMillisPart())
        .withSeconds(currentPeriod * timePerPeriod.toSecondsPart())
        .withMinutes(currentPeriod * timePerPeriod.toMinutesPart())
        .withPeriod(currentPeriod + 1);
    return current
        .withStatus(Status.PAUSED)
        .withTime(time);
  }
}
