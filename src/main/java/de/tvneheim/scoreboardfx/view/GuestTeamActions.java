package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.utils.LayoutUtils;
import de.tvneheim.scoreboardfx.viewmodel.GameService;
import de.tvneheim.scoreboardfx.model.Side;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuestTeamActions extends TeamActions {

  @Override
  public void onMinusScore(ActionEvent event) {
    GameService.minusScoreGuest();
  }

  public void onPlusScore(ActionEvent event) {
    GameService.scoreGuest();
  }

  @Override
  public void onTeamTimeOut(ActionEvent event) {
    GameService.requestTimeOut(Side.GUEST);
  }

  @Override
  public void onPenalty(ActionEvent event) {
    GameService.twoMinutesForGuest(4);
  }

  @Override
  public void onInit() {
    var root = LayoutUtils.getTopLevelRoot(plusGoalButton);
    LayoutUtils.mirrorLayout(root);
  }


}
