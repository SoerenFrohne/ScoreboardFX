package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyHomeAdded extends PenaltyAdded {

  public PenaltyHomeAdded(PenaltyType type, Player player, TimeStamp time) {
    super(TeamType.HOME, type, player, time);
  }
}
