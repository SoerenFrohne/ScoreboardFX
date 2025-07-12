package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyGuestAdded extends PenaltyAdded {

  public PenaltyGuestAdded(Penalty penalty) {
    super(penalty, TeamType.GUEST);
  }
}
