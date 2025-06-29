package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.model.Penalty;
import de.tvneheim.scoreboardfx.model.TimeStamp;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.java.Log;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.time;

@Log
public class PenaltyLabel extends Label {

  private final Penalty penalty;
  private final ChangeListener<TimeStamp> listener;

  public PenaltyLabel(Penalty penalty) {
    super();
    this.penalty = penalty;
    this.listener = (o, old, current) -> showRemainingTime(current);
    this.initialize();
  }

  public PenaltyLabel() {
    super();
    this.penalty = null;
    this.listener = null;
    this.initialize();
  }

  public void initialize() {

    this.setAlignment(Pos.CENTER);
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.getStyleClass().setAll("penalty");
    VBox.setVgrow(this, Priority.ALWAYS);

    if (penalty == null) {
      hide();
    } else {
      showRemainingTime(GameState.getStopWatch().getCurrentTime());
      GameState.getStopWatch().getTimestamp().addListener(listener);
    }
  }

  private void hide() {
    GameState.getStopWatch().getTimestamp().removeListener(listener);
    this.setText("Nr. 0 00:00");
    this.getStyleClass().add("hiddenPenalty");
  }

  private void showRemainingTime(TimeStamp current) {
    var remainingTime = penalty.calculateRemainingTime(current);
    this.setText("Nr. " + penalty.player().number().get() + " " + time(remainingTime.toMinutesPart(), remainingTime.toSecondsPart()));

    if (remainingTime.isZero() || remainingTime.isNegative()) {
      hide();
    }
  }
}
