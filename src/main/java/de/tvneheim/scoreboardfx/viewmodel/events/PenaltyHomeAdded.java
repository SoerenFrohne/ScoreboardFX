package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.*;

public class PenaltyHomeAdded extends PenaltyAdded {

  public PenaltyHomeAdded(Penalty penalty) {
    super(penalty, TeamType.HOME);
  }
}
