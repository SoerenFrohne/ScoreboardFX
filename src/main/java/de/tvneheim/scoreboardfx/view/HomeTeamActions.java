package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.GameService;
import de.tvneheim.scoreboardfx.model.Game;
import javafx.event.ActionEvent;
import lombok.extern.java.Log;

@Log
public class HomeTeamActions extends TeamActions {

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
