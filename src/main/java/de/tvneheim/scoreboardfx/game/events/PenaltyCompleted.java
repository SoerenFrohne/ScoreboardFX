package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PenaltyCompleted extends Event {

  private final Penalty penalty;

  @Override
  public String description() {
    return "Strafe abgelaufen für Spieler/-in Nr. " + penalty.player().number();
  }

  @Override
  public Game apply(Game current) {
    if (current.home().penalties().contains(penalty)) {
      var home = current.home().removePenalty(penalty);
      return current.withHome(home);
    } else if (current.guest().penalties().contains(penalty)) {
      var home = current.guest().removePenalty(penalty);
      return current.withGuest(home);
    } else {
      return current;
    }
  }

}
