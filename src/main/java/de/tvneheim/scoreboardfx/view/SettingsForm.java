package de.tvneheim.scoreboardfx.view;

import atlantafx.base.theme.Styles;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Section;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.dlsc.formsfx.view.util.ColSpan;
import de.tvneheim.scoreboardfx.game.GameState;
import de.tvneheim.scoreboardfx.infrastructure.settings.PredefinedTeam;
import de.tvneheim.scoreboardfx.infrastructure.settings.PredefinitionsLoader;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsForm extends VBox {

  public SettingsForm(EventHandler<ActionEvent> onClose, EventHandler<ActionEvent> onSubmit) {

    this.setAlignment(Pos.CENTER);
    setMinSize(-1, -1);
    setMaxSize(-1, -1);
    setStyle("-fx-background-color: -color-bg-default;");

    var teams = new SimpleListProperty<>(FXCollections.observableArrayList(PredefinitionsLoader.loadTeams()));

    var form = Form.of(
        Section.of(
            Field.ofSingleSelectionType(teams, GameState.getSettings().homeTeam())
                .select(0)
                .label("Heimteam")
                .span(ColSpan.HALF),
            Field.ofSingleSelectionType(teams.stream().map(PredefinedTeam::name).toList(), 0)
                .select(0)
                .label("Gastteam")
                .span(ColSpan.HALF),
            Field.ofIntegerType(GameState.getSettings().minutesPerPeriod())
                .label("Dauer pro Halbzeit")
                .span(ColSpan.HALF)
                .required(true),
            Field.ofIntegerType(GameState.getSettings().minutesBetweenPeriods())
                .label("Dauer der Pause")
                .span(ColSpan.HALF)
                .required(true)
        ).title("Zeiteinstellungen"),
        Section.of(
            Field.ofIntegerType(GameState.getSettings().minutesPerPeriod())
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
