package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Side;
import de.tvneheim.scoreboardfx.model.TimeOut;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamTimeOutAdded extends Event {

  private final Side side;
  private final TimeOut timeOut;

  @Override
  public EventType type() {
    return EventType.TIME_OUT_ADDED;
  }

  @Override
  public String description() {
    return "Team-Time-Out für " + (side == Side.HOME ? "das Heimteam" : "das Gastteam");
  }

  @Override
  public Game apply(Game current) {
    if (side == Side.HOME) {
      var home = current.home().addTimeOut(timeOut);
      return current.withHome(home);
    } else {
      var guest = current.guest().addTimeOut(timeOut);
      return current.withHome(guest);
    }
  }
}
