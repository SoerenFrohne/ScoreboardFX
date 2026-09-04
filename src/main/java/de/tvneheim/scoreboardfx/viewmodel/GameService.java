package de.tvneheim.scoreboardfx.viewmodel;

import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import de.tvneheim.scoreboardfx.model.*;
import de.tvneheim.scoreboardfx.view.NumberPad;
import de.tvneheim.scoreboardfx.viewmodel.events.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Popup;
import lombok.extern.java.Log;

import java.time.Duration;

import static de.tvneheim.scoreboardfx.viewmodel.GameState.getStopWatch;

@Log
public final class GameService {

  public static void changeName(Side side, String newName) {
    GameState.addEvent(new ChangedTeamName(side, newName));
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

  private static void requestTwoMinutes(Side side, ActionEvent actionEvent) {
    var source = (Node) actionEvent.getSource();

    var popup = new Popup();
    var numberPad = new NumberPad();
    popup.setAutoHide(true);
    popup.getContent().add(numberPad);

    numberPad.setOnConfirm(number -> {
      popup.hide();
      twoMinutes(side, number);
    });

    var bounds = source.localToScreen(source.getBoundsInLocal());

    popup.show(source, bounds.getMinX(), bounds.getMaxY() + 5);

  }

  private static void twoMinutes(Side side, int number) {

    var penalty = Penalty.twoMinutes(new Player(number), getElapsedTime());
    var event = side == Side.HOME ? new PenaltyHomeAdded(penalty) : new PenaltyGuestAdded(penalty);
    GameState.addEvent(event);

    var suspensionList = side == Side.HOME ? getStopWatch().getSuspensionsHome() : getStopWatch().getSuspensionsGuest();
    var suspension = new SuspensionTimer(penalty.player().number(), penalty.duration());
    suspensionList.add(suspension);

    suspension.completed().addListener((observable, oldValue, completed) -> {
      if (completed) {
        GameState.addEvent(new PenaltyCompleted(penalty));
        suspensionList.remove(suspension);
      }
    });
  }

  public static void twoMinutesForHome(ActionEvent actionEvent) {
    requestTwoMinutes(Side.HOME, actionEvent);
  }

  public static void twoMinutesForGuest(ActionEvent actionEvent) {
    requestTwoMinutes(Side.GUEST, actionEvent);
  }

  public static void skipTimeOut() {
    if (getStopWatch().getTimeOutTimer().running().get()) {
      getStopWatch().getTimeOutTimer().skip();
    }
  }

  public static void requestTimeOut(Side side) {
    var timer = getStopWatch().getTimeOutTimer();

    var timeOut = new TimeOut(
        getElapsedTime(),
        GameState.getSettings().timePerTeamTimeOut().getValue(),
        GameState.getSettings().timeOutWarningTime().getValue()
    );

    getStopWatch().pause();
    SoundBoard.honkMid();
    GameState.addEvent(new TeamTimeOutAdded(side, timeOut));

    timer.start();
    timer.overWarningTime().addListener(observable -> SoundBoard.honkShort());
    timer.running().addListener((observableValue, oldVal, newVal) -> {

      if (oldVal == true && newVal == false && !timer.skipped().get()) {
        SoundBoard.honkLong();
      }
    });
  }

  public static Duration getElapsedTime() {
    return getStopWatch().getPeriodTimer().getGameTime();
  }


}
