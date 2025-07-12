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
    var completed = penalty.withSuspensionCompleted(true);
    if (current.home().penalties().contains(penalty)) {
      return current.withHome(current.home().updatePenalty(penalty, completed));
    } else if (current.guest().penalties().contains(penalty)) {
      return current.withGuest(current.guest().updatePenalty(penalty, completed));
    } else {
      return current;
    }
  }

}
