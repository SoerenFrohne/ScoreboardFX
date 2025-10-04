package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.GameState;
import de.tvneheim.scoreboardfx.game.SuspensionTimer;
import de.tvneheim.scoreboardfx.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.doubleDigits;

public class SuspensionRow extends HBox {

  @FXML
  private TextField minutes, seconds, number;

  @FXML
  private Button deleteButton;

  public SuspensionRow(SuspensionTimer suspensionTimer) {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/penalty-row.fxml");

    if (suspensionTimer == null) {
      FXMLUtils.removeFromParent(this);
    } else {
      number.textProperty().bindBidirectional(suspensionTimer.number(), new NumberStringConverter());
      number.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());
      minutes.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());
      seconds.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());
      deleteButton.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());

      // Update text
      minutes.setText(doubleDigits(suspensionTimer.remainingTime().getValue().toMinutesPart()));
      seconds.setText(doubleDigits(suspensionTimer.remainingTime().getValue().toSecondsPart()));
      suspensionTimer.remainingTime().addListener((observable, oldValue, remainingTime) -> {
        minutes.setText(doubleDigits(remainingTime.toMinutesPart()));
        seconds.setText(doubleDigits(remainingTime.toSecondsPart()));
      });

      // Remove when completed
      suspensionTimer.completed().addListener((observable, oldValue, completed) -> {
        if (completed) {
          FXMLUtils.removeFromParent(this);
        }
      });

    }
  }

}
