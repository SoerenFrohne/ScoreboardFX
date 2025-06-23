package de.tvneheim.scoreboardfx;

import de.tvneheim.scoreboardfx.events.*;
import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Settings;
import de.tvneheim.scoreboardfx.model.TeamType;

public final class GameService {

  public static Game getState() {
    var currentGame = new Game("HEIM", "GAST", new Settings(30));
    for (Event event : EventStore.getEvents()) {
      currentGame = event.apply(currentGame);
    }
    return currentGame;
  }

  public static void startGame() {
    EventStore.addEvent(new GameStarted());
  }

  public static void changeName(TeamType teamType, String newName) {
    EventStore.addEvent(new ChangedTeamName(teamType, newName));

  }

  public static void stopTime(int minutes, int seconds, int millis) {
    EventStore.addEvent(new TimePaused(millis, seconds, minutes));
  }

  public static void startTime() {
    EventStore.addEvent(new TimeStarted());
  }

  public static void scoreHome() {
    EventStore.addEvent(new HomeScored());
  }

  public static void scoreGuest() {
    EventStore.addEvent(new GuestScored());
  }

  public static void stopPeriod() {
    EventStore.addEvent(new PeriodFinished());
  }
}
