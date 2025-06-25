package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.events.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.extern.java.Log;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Log
public class EventLabel extends HBox {

  private static int COUNT = 0;

  @FXML
  private Label dateLabel;
  @FXML
  private Label textLabel;
  @FXML
  private Button deleteButton;

  public EventLabel(Event event) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/tvneheim/scoreboardfx/fxml/event-label.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      initializeView(event);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }


  private void initializeView(Event event) {
    COUNT++;

    if (COUNT % 2 == 0) {
      this.setStyle("-fx-background-color: #FFF;");
    }

      dateLabel.setText(event.getCreated().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss")) + " Uhr");
    textLabel.setText(event.description());
    deleteButton.setText("");
    deleteButton.setGraphic(new Glyph("FontAwesome", FontAwesome.Glyph.CLOSE));
  }

}
