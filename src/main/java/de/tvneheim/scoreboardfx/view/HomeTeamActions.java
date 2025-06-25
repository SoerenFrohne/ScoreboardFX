package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.GameService;
import de.tvneheim.scoreboardfx.model.TeamType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;

@Log
public class HomeTeamActions extends TeamActions {

  @FXML
  private TextField teamName;

  public HomeTeamActions() {
    super();
    teamName.textProperty().addListener((observable, oldValue, newValue) -> GameService.changeName(TeamType.HOME, newValue));
  }

  public void onScore(ActionEvent event) {
    GameService.scoreHome();
  }

  @Override
  public void onTeamTimeOut(ActionEvent event) {
    //TODO
  }

  @Override
  public void onPenalty(ActionEvent event) {
    //TODO
  }

}
