package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.GameService;
import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.view.StopWatch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.extern.java.Log;

import java.net.URL;
import java.util.ResourceBundle;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.doubleDigits;

@Log
public class GameActionsController implements Initializable {

  @FXML
  private Label time;

  @FXML
  private Button startStopButton;

  @FXML
  private Label scoreHome;

  @FXML
  private Label scoreGuest;

  private final StopWatch stopWatch = GameState.getStopWatch();

  @FXML
  protected void toggleStartStop() {
    if (stopWatch.isRunning()) {
      GameService.stopTime();
    } else {
      GameService.startTime();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    time.textProperty().bind(GameState.getStopWatch().getTime());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(doubleDigits(game.home().score()));
      scoreGuest.setText(doubleDigits(game.guest().score()));
    });

    GameState.getStopWatch().getStopped()
        .addListener((observable, oldValue, newValue) -> startStopButton.setText(newValue ? "Start" : "Pause"));
  }
}
