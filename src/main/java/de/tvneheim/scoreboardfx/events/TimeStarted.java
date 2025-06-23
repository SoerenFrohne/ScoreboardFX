package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Time;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeStarted extends Event {

  @Override
  public String description() {
    return "Zeit gestartet";
  }

  @Override
  public Game apply(Game current) {
    var time = current.time().withPaused(false);
    return current.withTime(time);
  }
}
