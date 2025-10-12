package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.*;
import de.tvneheim.scoreboardfx.utils.FormatterUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class PenaltyAdded extends Event {

  private final Penalty penalty;
  private final TeamType teamType;

  @Override
  public EventType type() {
    return EventType.PENALTY_ADDED;
  }

  @Override
  public String description() {
    var player = penalty.player();
    return switch (penalty.type()) {
      case YELLOW -> "Verwarnung für Spieler/-in Nr. " + player.number() + "!";
      case TWO_MINUTES -> "Zeitstrafe für Spieler/-in Nr. " + player.number()
          + " (" + FormatterUtils.time(penalty.duration()) + ")!";
      case RED -> "Rote Karte gegen Spieler/-in Nr." + player.number() + "!";
      case BLUE -> "Rote Karte mit Bericht gegen Spieler/-in Nr. " + player.number();
    };
  }

  @Override
  public Game apply(Game current) {
    if (teamType == TeamType.HOME) {
      var home = current.home().addPenalty(penalty);
      return current.withHome(home);
    } else {
      var guest = current.guest().addPenalty(penalty);
      return current.withGuest(guest);
    }
  }


}
