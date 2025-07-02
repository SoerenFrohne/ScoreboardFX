package de.tvneheim.scoreboardfx.model;

import lombok.With;

@With
public record Game(
    Status status,
    Team home,
    Team guest,
    Time time,
    Settings settings
) {

  public Game(String homeTeamName, String guestTeamName, Settings settings) {
    this(
        Status.INITIAL,
        new Team(TeamType.HOME, homeTeamName),
        new Team(TeamType.GUEST, guestTeamName),
        new Time(),
        settings
    );
  }

  public Game reset() {
    return new Game(Status.INITIAL, home.reset(), guest.reset(), new Time(), settings);
  }
}
