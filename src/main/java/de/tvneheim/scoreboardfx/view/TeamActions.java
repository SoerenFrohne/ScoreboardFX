package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.viewmodel.GameService;
import de.tvneheim.scoreboardfx.viewmodel.GameState;
import de.tvneheim.scoreboardfx.utils.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public abstract class TeamActions extends VBox implements Initializable {

  @FXML
  private Button penaltyButton, timeOutButton, minusGoalButton, plusGoalButton;

  public TeamActions() {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/team-actions.fxml");
    plusGoalButton.setOnAction(this::onPlusScore);
    minusGoalButton.setOnAction(this::onMinusScore);
    penaltyButton.setOnAction(this::onPenalty);

    penaltyButton.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());

    timeOutButton.setOnAction(this::toggleTimeOut);
    GameState.getStopWatch().getTimeOutTimer().running().addListener((observableValue, old, isRunning) ->  {
      if(isRunning) {
        timeOutButton.setText("Abbrechen");
      } else {
        timeOutButton.setText("Time-Out");
      }
    });
  }

  public abstract void onMinusScore(ActionEvent event);

  public abstract void onPlusScore(ActionEvent event);

  public abstract void onTeamTimeOut(ActionEvent event);

  public abstract void onPenalty(ActionEvent event);

  private void toggleTimeOut(ActionEvent event) {
    if(GameState.getStopWatch().getTimeOutTimer().running().get()) {
      GameService.skipTimeOut();
    } else {
      onTeamTimeOut(event);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    log.info("Initialized");

  }
}
