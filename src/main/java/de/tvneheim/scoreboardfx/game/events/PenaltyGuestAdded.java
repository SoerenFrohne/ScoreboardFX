package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyGuestAdded extends PenaltyAdded {

  public PenaltyGuestAdded(PenaltyType type, Player player, TimeStamp time) {
    super(TeamType.GUEST, type, player, time);
  }
}
