package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.viewmodel.GameService;
import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.infrastructure.sound.SoundBoard;
import de.tvneheim.scoreboardfx.viewmodel.StopWatch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.*;
import static de.tvneheim.scoreboardfx.viewmodel.GameState.getStopWatch;
import static javafx.beans.binding.Bindings.not;

@Log
public class GameActionsController implements Initializable {

  @FXML
  private TextField clientTime, clientTimeOverwriter;

  @FXML
  private Button startStopButton, skipHalftime;

  @FXML
  private Label scoreHome, scoreGuest, periodInfo;

  private final StopWatch stopWatch = GameState.getStopWatch();

  @FXML
  protected void toggleStartStop() {
    if (stopWatch.getPeriodTimer().isRunning()) {
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

  @FXML
  protected void skipToNextPeriod() {
    getStopWatch().skipToNextPeriod();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    clientTime.managedProperty().bind(clientTime.visibleProperty());
    clientTime.textProperty().bind(bindFormattedTime(GameState.getStopWatch().getPeriodTimer().currentTime()));
    clientTime.setVisible(false);

    clientTimeOverwriter.managedProperty().bind(clientTimeOverwriter.visibleProperty());
    clientTimeOverwriter.focusedProperty().addListener((observableValue, old, focused) -> {
      log.info("Focused: " + focused);
      log.info("Text: " + clientTimeOverwriter);
      if (!focused) {
        try {
          var adjustedTime = durationFromString(clientTimeOverwriter.getText());
          GameState.getStopWatch().getPeriodTimer().currentTime().setValue(adjustedTime);
        } catch (Exception e) {
          var fallback = GameState.getStopWatch().getPeriodTimer().currentTime().get();
          clientTimeOverwriter.textProperty().setValue(time(fallback));
        }
      }
    });

    periodInfo.textProperty().bind(GameState.getStopWatch().getPeriodTimer().description());

    GameState.getGame().addListener((observable, oldValue, game) -> {
      scoreHome.setText(doubleDigits(game.home().score()));
      scoreGuest.setText(doubleDigits(game.guest().score()));
    });

    GameState.getStopWatch().getPeriodTimer().stopped().addListener((observable, oldValue, stopped) -> {
      startStopButton.setText(stopped ? "Start" : "Pause");
      var icon = stopped ? new FontIcon("fas-play") : new FontIcon("fas-pause");
      startStopButton.setGraphic(icon);

      toggleClientTimeFields(stopped);
    });

    skipHalftime.disableProperty().bind(not(getStopWatch().getPauseTimer().running()));
  }

  private void toggleClientTimeFields(Boolean stopped) {
    if (stopped) {
      clientTime.setVisible(false);
      clientTimeOverwriter.setVisible(true);
      clientTimeOverwriter.textProperty().setValue(clientTime.textProperty().getValue());
    } else {
      clientTime.setVisible(true);
      clientTimeOverwriter.setVisible(false);
    }
  }
}
