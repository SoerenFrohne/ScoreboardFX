package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.model.Penalty;
import de.tvneheim.scoreboardfx.view.PenaltyRow;
import de.tvneheim.scoreboardfx.view.TeamActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ScoreboardClientController implements Initializable {

  @FXML
  private TeamActions homeActions, guestActions;

  @FXML
  private VBox penaltiesHome, penaltiesGuest;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    GameState.getGame().addListener((observable, oldValue, game) -> {
      updatePenaltyRows(penaltiesHome, game.home().penalties());
      updatePenaltyRows(penaltiesGuest, game.guest().penalties());
    });
  }

  private void updatePenaltyRows(VBox root, List<Penalty> penalties) {
    root.getChildren().clear();
    penalties.forEach(penalty -> {
      var row = new PenaltyRow(penalty);
      root.getChildren().add(row);
    });
  }
}
