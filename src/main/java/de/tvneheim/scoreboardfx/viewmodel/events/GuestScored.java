package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;

public class GuestScored extends Event {

  @Override
  public EventType type() {
    return EventType.GUEST_SCORED;
  }

  @Override
  public String description() {
    return "Tor für die Gastmannschaft";
  }

  @Override
  public Game apply(Game current) {
    var newScore = current.guest().score() + 1;
    var guest = current.guest().withScore(newScore);
    return current.withGuest(guest);
  }
}
