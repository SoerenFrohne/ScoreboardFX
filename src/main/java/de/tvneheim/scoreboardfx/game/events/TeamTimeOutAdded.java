package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.TeamType;
import de.tvneheim.scoreboardfx.model.TimeOut;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamTimeOutAdded extends Event {

  private final TeamType teamType;
  private final TimeOut timeOut;

  @Override
  public String description() {
    return "Team-Time-Out für " + (teamType == TeamType.HOME ? "das Heimteam" : "das Gastteam");
  }

  @Override
  public Game apply(Game current) {
    if (teamType == TeamType.HOME) {
      var home = current.home().addTimeOut(timeOut);
      return current.withHome(home);
    } else {
      var guest = current.guest().addTimeOut(timeOut);
      return current.withHome(guest);
    }
  }
}
