package de.tvneheim.scoreboardfx.model;

import de.tvneheim.scoreboardfx.utils.ListUtils;
import lombok.With;

import java.util.List;

@With
public record Team(
    TeamType type,
    String name,
    String logo,
    int score,
    List<Player> players,
    List<Penalty> penalties
) {
  public Team(TeamType type, String name) {
    this(type, name, null, 0, List.of(), List.of());
  }

  public Team reset() {
    return new Team(type, name);
  }

  public Team addPenalty(Penalty penalty) {
    var list = ListUtils.appendElements(penalties, penalty);
    return this.withPenalties(list);
  }

  public Team removePenalty(Penalty penalty) {
    var list = ListUtils.removeElements(penalties, penalty);
    return this.withPenalties(list);
  }
}
