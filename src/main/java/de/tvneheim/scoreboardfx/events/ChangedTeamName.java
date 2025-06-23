package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.TeamType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangedTeamName extends Event {

  private final TeamType teamType;
  private final String newName;

  @Override
  public String description() {
    var team = teamType == TeamType.HOME ? "Gastgeber" : "Gäste";
    return "Änderung des Teamnamens der " + team + " auf " + newName;
  }

  @Override
  public Game apply(Game current) {
    if (teamType == TeamType.HOME) {
      var team = current.home().withName(newName);
      return current.withHome(team);
    } else {
      var team = current.guest().withName(newName);
      return current.withGuest(team);
    }
  }
}
