package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import de.tvneheim.scoreboardfx.model.*;
import de.tvneheim.scoreboardfx.viewmodel.events.*;
import lombok.extern.java.Log;

import java.time.Duration;

import static de.tvneheim.scoreboardfx.viewmodel.GameState.getStopWatch;

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
    StopWatch stopWatch = getStopWatch();
    stopWatch.pause();
    GameState.addEvent(new TimePaused(getElapsedTime()));
  }

  public static void startTime() {
    StopWatch stopWatch = getStopWatch();
    stopWatch.play();
    GameState.addEvent(new TimeStarted());
  }

  public static void scoreHome() {
    GameState.addEvent(new HomeScored());
  }

  public static void minusScoreHome() {
    GameState.removeEvent(GameState.findLastOfType(EventType.HOME_SCORED).getId());
  }

  public static void scoreGuest() {
    GameState.addEvent(new GuestScored());
  }

  public static void minusScoreGuest() {
    GameState.removeEvent(GameState.findLastOfType(EventType.GUEST_SCORED).getId());
  }

  public static void twoMinutesForHome(int number) {

    var penalty = Penalty.twoMinutes(new Player(number), getElapsedTime());
    GameState.addEvent(new PenaltyHomeAdded(penalty));

    var suspension = new SuspensionTimer(penalty.player().number(), penalty.duration());
    getStopWatch().getSuspensionsHome().add(suspension);

    suspension.completed().addListener((observable, oldValue, completed) -> {
      if (completed) {
        GameState.addEvent(new PenaltyCompleted(penalty));
        getStopWatch().getSuspensionsHome().remove(suspension);
      }
    });
  }

  public static void twoMinutesForGuest(int number) {
    var penalty = Penalty.twoMinutes(new Player(number), getElapsedTime());
    GameState.addEvent(new PenaltyGuestAdded(penalty));

    var suspension = new SuspensionTimer(penalty.player().number(), penalty.duration());
    getStopWatch().getSuspensionsGuest().add(suspension);

    suspension.completed().addListener((observable, oldValue, completed) -> {
      if (completed) {
        GameState.addEvent(new PenaltyCompleted(penalty));
        getStopWatch().getSuspensionsGuest().remove(suspension);
      }
    });
  }

  public static void requestTimeOut(TeamType teamType) {
    if (GameState.getCurrentGame().home().timeOuts().size() <= GameState.getSettings().maxTimeOutsPerPeriod().get()) {

      var timeOut = new TimeOut(
          getElapsedTime(),
          GameState.getSettings().timePerTeamTimeOut().getValue(),
          GameState.getSettings().timeOutWarningTime().getValue()
      );

      getStopWatch().pause();
      SoundBoard.honkShort();
      GameState.addEvent(new TeamTimeOutAdded(teamType, timeOut));

      getStopWatch().getTimeOutTimer().start();
      getStopWatch().getTimeOutTimer().overWarningTime().addListener(observable -> SoundBoard.honkShort());
      getStopWatch().getTimeOutTimer().running().addListener((observableValue, oldVal, newVal) -> {
        if (oldVal == true && newVal == false) {
          SoundBoard.honkLong();
        }
      });

    }
  }

  public static Duration getElapsedTime() {
    return getStopWatch().getPeriodTimer().getGameTime();
  }


}
