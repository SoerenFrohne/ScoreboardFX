package de.tvneheim.scoreboardfx.controller;

import atlantafx.base.controls.ModalPane;
import de.tvneheim.scoreboardfx.GameService;
import de.tvneheim.scoreboardfx.events.Event;
import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.model.Penalty;
import de.tvneheim.scoreboardfx.view.EventLabel;
import de.tvneheim.scoreboardfx.view.PenaltyRow;
import de.tvneheim.scoreboardfx.view.SettingsForm;
import de.tvneheim.scoreboardfx.view.TeamActions;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.extern.java.Log;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static de.tvneheim.scoreboardfx.utils.LayoutUtils.setExactWidth;

@Log
public class ScoreboardClientController implements Initializable {

  @FXML
  private StackPane stack;

  @FXML
  private BorderPane rootPane;

  @FXML
  private TeamActions homeActions, guestActions;

  @FXML
  private VBox penaltiesHome, penaltiesGuest, gameActions, history;

  @FXML
  private ScrollPane events;

  private final ModalPane modal = new ModalPane();

  @FXML
  public void openGameSettings() {
    var settings = new SettingsForm(
        event -> modal.hide(true),
        event -> {
          modal.hide(true);
          GameService.initializeGame();
        }
    );
    modal.show(settings);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    stack.getChildren().add(modal);

    initSizes();

    GameState.getGame().addListener((observable, oldValue, game) -> {
      updatePenaltyRows(penaltiesHome, game.home().penalties());
      updatePenaltyRows(penaltiesGuest, game.guest().penalties());
    });

    GameState.getEventsProperty().addListener((ListChangeListener<? super Event>) this::updateHistory);
  }


  private void initSizes() {
    Platform.runLater(() -> {
      setExactWidth(penaltiesHome, homeActions.getWidth());
      setExactWidth(penaltiesGuest, guestActions.getWidth());
      setExactWidth(events, gameActions.getWidth());

      rootPane.getScene().widthProperty().addListener(observable -> {
        setExactWidth(penaltiesHome, homeActions.getWidth());
        setExactWidth(penaltiesGuest, guestActions.getWidth());
        setExactWidth(events, gameActions.getWidth());
      });

      // Scroll automatically to bottom on new events
      history.heightProperty().addListener(observable -> events.setVvalue(1D));

    });
  }

  private void updatePenaltyRows(VBox root, List<Penalty> penalties) {
    root.getChildren().clear();
    penalties.forEach(penalty -> {
      var row = new PenaltyRow(penalty);
      root.getChildren().add(row);
    });
  }

  private void updateHistory(ListChangeListener.Change<? extends Event> change) {
    history.getChildren().clear();

    var events = change.getList().stream()
        .sorted(Comparator.comparing(Event::getCreated))
        .toList();

    var labels = IntStream.range(0, events.size())
        .mapToObj(i -> new EventLabel(events.get(i), i))
        .toList();

    history.getChildren().addAll(labels);
  }

}
