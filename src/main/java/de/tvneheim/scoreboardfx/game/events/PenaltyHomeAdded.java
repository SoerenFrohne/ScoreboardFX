package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyHomeAdded extends PenaltyAdded {

  public PenaltyHomeAdded(Penalty penalty) {
    super(penalty, TeamType.HOME);
  }
}
