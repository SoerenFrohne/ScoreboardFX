package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyGuestAdded extends PenaltyAdded {

  public PenaltyGuestAdded(PenaltyType type, Player player, TimeStamp time) {
    super(TeamType.GUEST, type, player, time);
  }
}
