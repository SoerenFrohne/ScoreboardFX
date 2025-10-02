package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.game.GameService;
import de.tvneheim.scoreboardfx.game.GameState;
import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import de.tvneheim.scoreboardfx.game.StopWatch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.bindFormattedTime;
import static de.tvneheim.scoreboardfx.utils.FormatterUtils.doubleDigits;

@Log
public class GameActionsController implements Initializable {

  @FXML
  private Label clientTime;

  @FXML
  private Button startStopButton;

  @FXML
  private Label scoreHome, scoreGuest, periodInfo;

  private final StopWatch stopWatch = GameState.getStopWatch();

  @FXML
  protected void toggleStartStop() {
    if (stopWatch.getPeriodTime().isRunning()) {
      GameService.stopTime();
    } else {
      GameService.startTime();
    }
  }

  @FXML
  @SneakyThrows
  protected void honk() {
    SoundBoard.honkShort();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    clientTime.textProperty().bind(bindFormattedTime(GameState.getStopWatch().getPeriodTime().gameTime()));

    periodInfo.textProperty().bind(GameState.getStopWatch().getPeriodTime().period());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(doubleDigits(game.home().score()));
      scoreGuest.setText(doubleDigits(game.guest().score()));
    });

    GameState.getStopWatch().getPeriodTime().stopped().addListener((observable, oldValue, stopped) -> {
      startStopButton.setText(stopped ? "Start" : "Pause");
      var icon = stopped ? new FontIcon("fas-play") : new FontIcon("fas-pause");
      startStopButton.setGraphic(icon);
    });
  }
}
