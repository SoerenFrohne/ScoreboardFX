package de.tvneheim.scoreboardfx.model;

import lombok.With;

@With
public record Game(
    Status status,
    Team home,
    Team guest,
    Time time
) {

  public Game(String homeTeamName, String guestTeamName) {
    this(
        Status.INITIAL,
        new Team(Side.HOME, homeTeamName),
        new Team(Side.GUEST, guestTeamName),
        new Time()
    );
  }

  public Game reset() {
    return new Game(Status.INITIAL, home.reset(), guest.reset(), new Time());
  }
}
