package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class PenaltyAdded extends Event {

  private final TeamType teamType;
  private final PenaltyType penaltyType;
  private final Player player;
  private final TimeStamp time;

  @Override
  public String description() {
    return switch (penaltyType) {
      case YELLOW -> "Verwarnung für Spieler/-in Nr. " + player.number() + "!";
      case TWO_MINUTES -> "Zeitstrafe für Spieler/-in Nr. " + player.number() + "!";
      case RED -> "Rote Karte gegen Spieler/-in Nr." + player.number() + "!";
      case BLUE -> "Rote Karte mit Bericht gegen Spieler/-in Nr. " + player.number();
    };
  }

  @Override
  public Game apply(Game current) {
    return switch (penaltyType) {
      case YELLOW -> applyPenalty(teamType, current, Penalty.yellow(player, time));
      case TWO_MINUTES -> applyPenalty(teamType, current, Penalty.twoMinutes(player, time));
      case RED -> applyPenalty(teamType, current, Penalty.red(player, time));
      case BLUE -> applyPenalty(teamType, current, Penalty.blue(player, time));
    };
  }

  private Game applyPenalty(TeamType team, Game game, Penalty penalty) {
    if (team == TeamType.HOME) {
      var home = game.home().addPenalty(penalty);
      return game.withHome(home);
    } else {
      var guest = game.guest().addPenalty(penalty);
      return game.withGuest(guest);
    }
  }

}
