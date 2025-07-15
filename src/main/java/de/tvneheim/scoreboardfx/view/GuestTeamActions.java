package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.GameService;
import de.tvneheim.scoreboardfx.model.TeamType;
import javafx.event.ActionEvent;
import lombok.extern.java.Log;

@Log
public class GuestTeamActions extends TeamActions {

  public void onScore(ActionEvent event) {
    GameService.scoreGuest();
  }

  @Override
  public void onTeamTimeOut(ActionEvent event) {
    GameService.requestTimeOut(TeamType.GUEST);
  }

  @Override
  public void onPenalty(ActionEvent event) {
    GameService.twoMinutesForGuest(4);
  }


}
