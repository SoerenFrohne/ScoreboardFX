package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Side;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangedTeamName extends Event {

  private final Side side;
  private final String newName;

  @Override
  public EventType type() {
    return EventType.CHANGED_TEAM_NAME;
  }

  @Override
  public String description() {
    var team = side == Side.HOME ? "Gastgeber" : "Gäste";
    return "Änderung des Teamnamens der " + team + " auf " + newName;
  }

  @Override
  public Game apply(Game current) {
    if (side == Side.HOME) {
      var team = current.home().withName(newName);
      return current.withHome(team);
    } else {
      var team = current.guest().withName(newName);
      return current.withGuest(team);
    }
  }
}
