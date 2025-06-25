package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.events.Event;
import de.tvneheim.scoreboardfx.events.GameState;
import de.tvneheim.scoreboardfx.view.EventLabel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import lombok.extern.java.Log;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Log
public class HistoryController implements Initializable {

  @FXML
  private VBox historyContainer;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    historyContainer.getChildren().clear();

    GameState.getProperty().addListener((ListChangeListener<? super Event>) this::updateHistory);
  }

  private void updateHistory(ListChangeListener.Change<? extends Event> change) {
    historyContainer.getChildren().clear();
    var labels = change.getList().stream()
        .sorted(Comparator.comparing(Event::getCreated))
        .map(EventLabel::new)
        .toList()
        .reversed();
    historyContainer.getChildren().addAll(labels);
  }

}
