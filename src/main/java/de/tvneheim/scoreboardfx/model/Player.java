package de.tvneheim.scoreboardfx.model;


public record Player(
    int number,
    String firstName,
    String secondName,
    int goals
) {

  public Player(int number) {
    this(number, "", "", 0);
  }
}
