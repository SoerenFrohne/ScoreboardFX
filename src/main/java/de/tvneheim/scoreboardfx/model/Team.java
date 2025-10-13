package de.tvneheim.scoreboardfx.model;

import de.tvneheim.scoreboardfx.utils.ListUtils;
import lombok.With;

import java.util.List;

@With
public record Team(
    Side type,
    String name,
    String logo,
    int score,
    List<Player> players,
    List<Penalty> penalties,
    List<TimeOut> timeOuts
) {
  public Team(Side type, String name) {
    this(type, name, null, 0, List.of(), List.of(), List.of());
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

  public Team addTimeOut(TimeOut timeOut) {
    var list = ListUtils.appendElements(timeOuts, timeOut);
    return this.withTimeOuts(list);
  }

  public Team removeTimeOut(TimeOut timeOut) {
    var list = ListUtils.removeElements(timeOuts, timeOut);
    return this.withTimeOuts(list);
  }

  public List<Penalty> currentPenalties() {
    return penalties.stream().filter(Penalty::isRunning).toList();
  }

  public Team updatePenalty(Penalty penalty, Penalty updated) {
    if (penalties.contains(penalty)) {
      return this.removePenalty(penalty).addPenalty(updated);
    } else {
      return this;
    }
  }
}
