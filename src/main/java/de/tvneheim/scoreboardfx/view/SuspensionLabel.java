package de.tvneheim.scoreboardfx.view;

import atlantafx.base.util.Animations;
import de.tvneheim.scoreboardfx.game.Suspension;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.java.Log;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.time;

@SuppressWarnings("CodeBlock2Expr")
@Log
public class SuspensionLabel extends Label {

  private final Suspension suspension;

  public SuspensionLabel(Suspension suspension) {
    super();
    this.suspension = suspension;
    this.initialize();
  }

  public SuspensionLabel() {
    super();
    this.suspension = null;
    this.initialize();
  }

  public void initialize() {

    this.setAlignment(Pos.CENTER);
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.getStyleClass().setAll("penalty");
    VBox.setVgrow(this, Priority.ALWAYS);

    if (suspension == null) {
      hide();
    } else {

      // update text
      this.setText("Nr. " + suspension.number().get() + " " + time(suspension.remainingTime().getValue()));
      suspension.remainingTime().addListener((observable, oldValue, remainingTime) -> {
        this.setText("Nr. " + suspension.number().get() + " " + time(remainingTime));
      });

      // hide when finished
      suspension.completed().addListener((observable, oldValue, completed) -> {
        if (completed) {
          var animation = Animations.shakeX(this);
          animation.playFromStart();
          animation.setOnFinished(event -> {
            hide();
          });
        }
      });
    }
  }

  private void hide() {
    this.setText("Nr. 0 00:00");
    this.getStyleClass().add("hiddenPenalty");
  }

}
