package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.GameState;
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
  private Button goalButton;

  @FXML
  private Button timeOutButton;

  @FXML
  private Button penaltyButton;

  public TeamActions() {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/team-actions.fxml");
    goalButton.setOnAction(this::onScore);
    timeOutButton.setOnAction(this::onTeamTimeOut);
    penaltyButton.setOnAction(this::onPenalty);

    penaltyButton.disableProperty().bind(GameState.getStopWatch().getPeriodTimer().stopped().not());
  }

  public abstract void onScore(ActionEvent event);

  public abstract void onTeamTimeOut(ActionEvent event);

  public abstract void onPenalty(ActionEvent event);

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    log.info("Initialized");

  }
}
