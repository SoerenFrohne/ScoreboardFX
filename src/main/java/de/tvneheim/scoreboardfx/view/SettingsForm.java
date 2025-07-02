package de.tvneheim.scoreboardfx.view;

import atlantafx.base.theme.Styles;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Section;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.dlsc.formsfx.view.util.ColSpan;
import de.tvneheim.scoreboardfx.events.GameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class SettingsForm extends VBox {

  public SettingsForm(EventHandler<ActionEvent> onClose, EventHandler<ActionEvent> onSubmit) {

    this.setAlignment(Pos.CENTER);
    setMinSize(-1, -1);
    setMaxSize(-1, -1);
    setStyle("-fx-background-color: -color-bg-default;");

    var form = Form.of(
            Section.of(
                Field.ofSingleSelectionType(List.of("TV Neheim 1884", "HV Sundern", "TS Evingsen", "SG Menden Sauerland Wölfe"))
                    .label("Heimteam"),
                Field.ofIntegerType(GameState.getSettings().timePerPeriod())
                    .label("Dauer pro Halbzeit")
                    .span(ColSpan.HALF)
                    .required(true),
                Field.ofIntegerType(GameState.getSettings().pauseBetweenPeriods())
                    .label("Dauer der Pause")
                    .span(ColSpan.HALF)
                    .required(true)
            ).title("Zeiteinstellungen"),
            Section.of(
                Field.ofIntegerType(GameState.getSettings().timePerPeriod())
                    .label("Werbeanzeigen-Intervall (Sekunden)")
                    .required(true),
                Field.ofStringType(GameState.getSettings().pathToAdImages())
                    .styleClass("formTextField")
                    .label("Werbeanzeigen-Verzeichnis"),
                Field.ofStringType(GameState.getSettings().pathToLogos())
                    .styleClass("formTextField")
                    .label("Vereinswappen-Verzeichnis")
            ).title("Ressourcen")
        ).title("Spieleinstellungen");


    var cancelButton = new Button("Abbrechen");
    cancelButton.getStyleClass().add(Styles.DANGER);
    cancelButton.setOnAction(event -> {
      form.reset();
      onClose.handle(event);
    });

    var saveButton = new Button("Neues Spiel starten");
    cancelButton.getStyleClass().add(Styles.SUCCESS);
    saveButton.setOnAction(event -> {
      form.persist();
      onSubmit.handle(event);
    });

    this.getChildren().addAll(new FormRenderer(form), new HBox(cancelButton, saveButton));
  }

}
