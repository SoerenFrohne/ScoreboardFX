package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.GameState;
import de.tvneheim.scoreboardfx.game.Suspension;
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

  public SuspensionRow(Suspension suspension) {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/penalty-row.fxml");

    if (suspension == null) {
      FXMLUtils.removeFromParent(this);
    } else {
      number.textProperty().bindBidirectional(suspension.number(), new NumberStringConverter());
      number.disableProperty().bind(GameState.getStopWatch().getPeriodTime().stopped().not());
      minutes.disableProperty().bind(GameState.getStopWatch().getPeriodTime().stopped().not());
      seconds.disableProperty().bind(GameState.getStopWatch().getPeriodTime().stopped().not());
      deleteButton.disableProperty().bind(GameState.getStopWatch().getPeriodTime().stopped().not());

      // Update text
      minutes.setText(doubleDigits(suspension.remainingTime().getValue().toMinutesPart()));
      seconds.setText(doubleDigits(suspension.remainingTime().getValue().toSecondsPart()));
      suspension.remainingTime().addListener((observable, oldValue, remainingTime) -> {
        minutes.setText(doubleDigits(remainingTime.toMinutesPart()));
        seconds.setText(doubleDigits(remainingTime.toSecondsPart()));
      });

      // Remove when completed
      suspension.completed().addListener((observable, oldValue, completed) -> {
        if (completed) {
          FXMLUtils.removeFromParent(this);
        }
      });

    }
  }

}
