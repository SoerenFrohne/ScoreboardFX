package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.GameState;
import de.tvneheim.scoreboardfx.model.Penalty;
import de.tvneheim.scoreboardfx.model.TimeStamp;
import de.tvneheim.scoreboardfx.utils.FXMLUtils;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.doubleDigits;

public class PenaltyRow extends HBox {

  private final Penalty penalty;
  private final ChangeListener<TimeStamp> listener;

  @FXML
  private TextField minutes, seconds, number;

  @FXML
  private Button deleteButton;

  public PenaltyRow(Penalty penalty) {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/penalty-row.fxml");
    this.penalty = penalty;
    this.listener = (o, old, current) -> showRemainingTime(current);

    if (penalty == null) {
      remove();
    } else {
      number.textProperty().bindBidirectional(penalty.player().number(), new NumberStringConverter());
      number.disableProperty().bind(GameState.getStopWatch().getStopped().not());
      minutes.disableProperty().bind(GameState.getStopWatch().getStopped().not());
      seconds.disableProperty().bind(GameState.getStopWatch().getStopped().not());
      deleteButton.disableProperty().bind(GameState.getStopWatch().getStopped().not());

      GameState.getStopWatch().getTimestamp().addListener(listener);
    }
  }

  private void showRemainingTime(TimeStamp current) {
    var remainingTime = penalty.calculateRemainingTime(current);
    minutes.setText(doubleDigits(remainingTime.toMinutesPart()));
    seconds.setText(doubleDigits(remainingTime.toSecondsPart()));

    if (remainingTime.isZero() || remainingTime.isNegative()) {
      remove();
    }
  }

  private void remove() {
    FXMLUtils.removeFromParent(this);
    GameState.getStopWatch().getTimestamp().removeListener(listener);
  }
}
