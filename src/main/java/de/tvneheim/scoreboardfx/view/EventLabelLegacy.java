package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.game.events.Event;
import de.tvneheim.scoreboardfx.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.extern.java.Log;

import java.time.format.DateTimeFormatter;

@Log
public class EventLabelLegacy extends HBox {

  private static int COUNT = 0;

  @FXML
  private Label dateLabel;
  @FXML
  private Label textLabel;
  @FXML
  private Button deleteButton;

  public EventLabelLegacy(Event event) {
    FXMLUtils.loadXml(this, "/de/tvneheim/scoreboardfx/fxml/event-label.fxml");
    initializeView(event);
  }


  private void initializeView(Event event) {
    COUNT++;

    if (COUNT % 2 == 0) {
      this.setStyle("-fx-background-color: #FFF;");
    }

    dateLabel.setText(event.getCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) + " Uhr");
    textLabel.setText(event.description());
  }

}
