package de.tvneheim.scoreboardfx.model;

import de.tvneheim.scoreboardfx.utils.ListUtils;
import lombok.With;

import java.util.List;

@With
public record Team(
        TeamType type,
        String name,
        int score,
        List<Player> players,
        List<Penalty> penalties
) {
    public Team(TeamType type, String name) {
        this(type, name, 0, List.of(), List.of());
    }

    public Team addPenalty(Penalty penalty) {
        var list = ListUtils.appendElements(penalties, penalty);
        return this.withPenalties(list);
    }

  public Team reset() {
    return new Team(type, name);
  }
}
