package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyHomeAdded extends PenaltyAdded {

  public PenaltyHomeAdded(PenaltyType type, Player player, TimeStamp time) {
    super(TeamType.HOME, type, player, time);
  }
}
