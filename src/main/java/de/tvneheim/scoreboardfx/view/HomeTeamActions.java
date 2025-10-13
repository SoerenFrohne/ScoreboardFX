package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.viewmodel.GameService;
import de.tvneheim.scoreboardfx.model.Side;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HomeTeamActions extends TeamActions {

  @FXML
  private TextField teamName;

  public HomeTeamActions() {
    super();
    teamName.textProperty().addListener((observable, oldValue, newValue) -> GameService.changeName(Side.HOME, newValue));
  }

  @Override
  public void onMinusScore(ActionEvent event) {
    GameService.minusScoreHome();
  }

  public void onPlusScore(ActionEvent event) {
    GameService.scoreHome();
  }

  @Override
  public void onTeamTimeOut(ActionEvent event) {
    GameService.requestTimeOut(Side.HOME);
  }

  @Override
  public void onPenalty(ActionEvent event) {
    GameService.twoMinutesForHome(4);
  }

}
