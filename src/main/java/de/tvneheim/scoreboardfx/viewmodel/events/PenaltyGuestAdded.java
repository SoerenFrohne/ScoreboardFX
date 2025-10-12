package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyGuestAdded extends PenaltyAdded {

  public PenaltyGuestAdded(Penalty penalty) {
    super(penalty, TeamType.GUEST);
  }
}
