package de.tvneheim.scoreboardfx.view;

import atlantafx.base.util.Animations;
import de.tvneheim.scoreboardfx.viewmodel.SuspensionTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.time;

@SuppressWarnings("CodeBlock2Expr")
@Slf4j
public class SuspensionLabel extends Label {

  private final SuspensionTimer suspension;

  public SuspensionLabel(SuspensionTimer suspensionTimer) {
    super();
    this.suspension = suspensionTimer;
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
      this.setText(suspension.number().get() + " " + time(suspension.remainingTime().getValue()));
      suspension.remainingTime().addListener((observable, oldValue, remainingTime) -> {
        log.info("Changed remaining time to {}", remainingTime.toMillis());
        this.setText(suspension.number().get() + " " + time(remainingTime));
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
    this.setText("0 00:00");
    this.getStyleClass().add("hiddenPenalty");
  }

}
