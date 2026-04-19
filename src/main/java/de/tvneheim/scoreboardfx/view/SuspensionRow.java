package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.viewmodel.SuspensionTimer;
import de.tvneheim.scoreboardfx.utils.FXMLUtils;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.doubleDigits;
import static de.tvneheim.scoreboardfx.viewmodel.GameState.getStopWatch;

@Slf4j
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
      number.disableProperty().bind(getStopWatch().getPeriodTimer().stopped().not());

      bindTime(suspensionTimer);

      deleteButton.disableProperty().bind(getStopWatch().getPeriodTimer().stopped().not());
      deleteButton.setOnAction(event -> suspensionTimer.completed().setValue(true));

      // Remove when completed
      suspensionTimer.completed().addListener((observable, oldValue, completed) -> {
        if (completed) {
          FXMLUtils.removeFromParent(this);
        }
      });

    }
  }

  private void bindTime(SuspensionTimer suspension) {

    // Initiale View
    minutes.setText(String.valueOf(suspension.remainingTime().get().toMinutes()));
    seconds.setText(String.valueOf(suspension.remainingTime().get().toSecondsPart()));

    // View → ViewModel
    minutes.textProperty().addListener(observable -> {
      if (GameState.isPaused()) {
        var min = parse(minutes.textProperty());
        var sec = parse(seconds.textProperty());
        suspension.setRemainingTime(min, sec);
      }
    });
    seconds.textProperty().addListener(observable -> {
      if (GameState.isPaused()) {
        var min = parse(minutes.textProperty());
        var sec = parse(seconds.textProperty());
        suspension.setRemainingTime(min, sec);
      }
    });

    // ViewModel → View
    suspension.remainingTime().addListener((obs, oldDur, remainingTime) -> {
      if (GameState.isRunning()) {
        minutes.setText(doubleDigits(remainingTime.toMinutesPart()));
        seconds.setText(doubleDigits(remainingTime.toSecondsPart()));
      }
    });
  }

  public int parse(StringProperty property) {
    try {
      return Integer.parseInt(property.get());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

}
