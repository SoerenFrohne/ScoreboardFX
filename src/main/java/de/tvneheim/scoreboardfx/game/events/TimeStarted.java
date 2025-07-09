package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.Game;
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
