package de.tvneheim.scoreboardfx.view;

import atlantafx.base.util.Animations;
import de.tvneheim.scoreboardfx.model.Side;
import de.tvneheim.scoreboardfx.viewmodel.SuspensionTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static de.tvneheim.scoreboardfx.utils.FormatterUtils.time;

@SuppressWarnings("CodeBlock2Expr")
@Slf4j
public class SuspensionLabel extends Label {

  private final SuspensionTimer suspension;

  private final Side side;

  public SuspensionLabel(SuspensionTimer suspensionTimer, Side side) {
    super();
    this.suspension = suspensionTimer;
    this.side = side;
    this.initialize();
  }

  public SuspensionLabel(Side side) {
    super();
    this.side = side;
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
      this.setText(formatSuspension(side, suspension.number().get(), suspension.remainingTime().get()));
      suspension.remainingTime().addListener((observable, oldValue, remainingTime) -> {
        log.info("Changed remaining time to {}", remainingTime.toMillis());
        this.setText(formatSuspension(side, suspension.number().get(), remainingTime));
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
    this.setText(formatSuspension(side, 0, Duration.ZERO));
    this.getStyleClass().add("hiddenPenalty");
  }

  private static String formatSuspension(Side side, int number, Duration duration) {
    return side == Side.HOME ? number + " " + time(duration) : time(duration) + " " + number;
  }

}
