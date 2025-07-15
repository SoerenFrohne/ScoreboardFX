package de.tvneheim.scoreboardfx.game;

import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import de.tvneheim.scoreboardfx.model.*;
import de.tvneheim.scoreboardfx.game.events.*;
import lombok.extern.java.Log;

@Log
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
    GameState.addEvent(new PeriodFinished(GameState.getStopWatch().getPeriod().get()));
  }

  public static void twoMinutesForHome(int number) {

    var penalty = Penalty.twoMinutes(new Player(number), getCurrentTimestamp());
    GameState.addEvent(new PenaltyHomeAdded(penalty));

    var suspension = new Suspension(penalty);
    GameState.getStopWatch().getSuspensionsHome().add(suspension);

    suspension.completed().addListener((observable, oldValue, completed) -> {
      if (completed) {
        GameState.addEvent(new PenaltyCompleted(penalty));
        GameState.getStopWatch().getSuspensionsHome().remove(suspension);
      }
    });
  }

  public static void twoMinutesForGuest(int number) {
    var penalty = Penalty.twoMinutes(new Player(number), getCurrentTimestamp());
    GameState.addEvent(new PenaltyGuestAdded(penalty));

    var suspension = new Suspension(penalty);
    GameState.getStopWatch().getSuspensionsGuest().add(suspension);

    suspension.completed().addListener((observable, oldValue, completed) -> {
      if (completed) {
        GameState.addEvent(new PenaltyCompleted(penalty));
        GameState.getStopWatch().getSuspensionsGuest().remove(suspension);
      }
    });
  }

  public static void requestTimeOut(TeamType teamType) {
    if (GameState.getCurrentGame().home().timeOuts().size() <= GameState.getSettings().maxTimeOutsPerPeriod().get()) {

      var timeOut = new TimeOut(
          GameState.getStopWatch().getDuration(),
          GameState.getSettings().timePerTeamTimeOut().getValue(),
          GameState.getSettings().timeOutWarningTime().getValue()
      );

      GameState.getStopWatch().pause();
      SoundBoard.honkShort();
      GameState.addEvent(new TeamTimeOutAdded(teamType, timeOut));

      var teamTimeOut = new TeamTimeOut(timeOut);
      GameState.getStopWatch().getTimeOutsHome().add(teamTimeOut);

      teamTimeOut.remainingTime().addListener((observable, oldValue, newValue) -> {
        if (newValue.compareTo(teamTimeOut.warningTime().getValue()) <= 0) {
          SoundBoard.honkShort();
        }
      });

      teamTimeOut.completed().addListener((observable, oldValue, completed) -> {
        if (completed) {
          SoundBoard.honkLong();
        }
      });

    }
  }


  public static TimeStamp getCurrentTimestamp() {
    return GameState.getStopWatch().getCurrentTime();
  }

}
