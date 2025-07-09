package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Time;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameStarted extends Event {

  @Override
  public String description() {
    return "Spiel gestartet.";
  }

  @Override
  public Game apply(Game current) {
    var time = new Time().withPaused(false);
    return current.withTime(time);
  }
}
