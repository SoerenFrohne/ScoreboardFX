package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;

public class HomeScored extends Event {

  @Override
  public EventType type() {
    return EventType.HOME_SCORED;
  }

  @Override
  public String description() {
    return "Tor für die Heimmannschaft";
  }

  @Override
  public Game apply(Game current) {
    var newScore = current.home().score() + 1;
    var home = current.home().withScore(newScore);
    return current.withHome(home);
  }
}
