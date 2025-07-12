package de.tvneheim.scoreboardfx.view;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import de.tvneheim.scoreboardfx.game.events.Event;
import javafx.scene.control.Button;
import lombok.extern.java.Log;
import org.kordamp.ikonli.javafx.FontIcon;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.dateTime;

@Log
public class EventLabel extends Tile {

  public EventLabel(Event event, int index) {

    this.setTitle(event.description());
    this.setDescription(dateTime(event.getCreated()));
    var btn = new Button(null, new FontIcon("fas-times"));
    btn.getStyleClass().addAll(Styles.BUTTON_CIRCLE, Styles.FLAT);
    this.setAction(btn);

    if (index % 2 == 0) {
      this.setStyle("-fx-background-color: -color-bg-subtle;");
    }
  }
}
