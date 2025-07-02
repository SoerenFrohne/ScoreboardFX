package de.tvneheim.scoreboardfx;

import de.tvneheim.scoreboardfx.events.*;
import de.tvneheim.scoreboardfx.model.*;
import de.tvneheim.scoreboardfx.view.StopWatch;

public final class GameService {

  public static void initializeGame() {
    GameState.restart();
  }

  public static void startGame() {
    GameState.addEvent(new GameStarted());
  }

  public static void changeName(TeamType teamType, String newName) {
    GameState.addEvent(new ChangedTeamName(teamType, newName));
  }

  public static void stopTime() {
    StopWatch stopWatch = GameState.getStopWatch();
    stopWatch.pause();
    GameState.addEvent(new TimePaused(stopWatch.getCurrentTime()));
  }

  public static void startTime() {
    StopWatch stopWatch = GameState.getStopWatch();
    stopWatch.start();
    GameState.addEvent(new TimeStarted());
  }

  public static void scoreHome() {
    GameState.addEvent(new HomeScored());
  }

  public static void scoreGuest() {
    GameState.addEvent(new GuestScored());
  }

  public static void stopPeriod() {
    GameState.addEvent(new PeriodFinished());
  }

  public static void twoMinutesForHome(int number) {
    GameState.addEvent(new PenaltyHomeAdded(PenaltyType.TWO_MINUTES, new Player(number), getCurrentTimestamp()));
  }

  public static void twoMinutesForGuest(int number) {
    GameState.addEvent(new PenaltyGuestAdded(PenaltyType.TWO_MINUTES, new Player(number), getCurrentTimestamp()));
  }

  public static TimeStamp getCurrentTimestamp() {
    return GameState.getStopWatch().getCurrentTime();
  }

}
