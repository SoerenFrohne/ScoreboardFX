package de.tvneheim.scoreboardfx.view;

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
    timeOutButton.setOnAction(this::onTeamTimeOut);
    penaltyButton.setOnAction(this::onPenalty);

    penaltyButton.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());
  }

  public abstract void onMinusScore(ActionEvent event);

  public abstract void onPlusScore(ActionEvent event);

  public abstract void onTeamTimeOut(ActionEvent event);

  public abstract void onPenalty(ActionEvent event);

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    log.info("Initialized");

  }
}
