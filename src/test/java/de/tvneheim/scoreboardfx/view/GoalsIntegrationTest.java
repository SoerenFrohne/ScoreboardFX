package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.controller.ScoreboardClientController;
import de.tvneheim.scoreboardfx.controller.ScoreboardViewController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class GoalsIntegrationTest {

  Stage view;
  Stage client;

  @Start
  @SneakyThrows
  void start(Stage primary) {
    view = ScoreboardViewController.show();
    client = ScoreboardClientController.show();
  }

  @Test
  void capturesGoals(FxRobot robot) {
    var homeGoalsLabel = (Label) view.getScene().lookup("#scoreHome");
    var guestGoalsLabel = (Label) view.getScene().lookup("#scoreGuest");

    robot.interact(() -> client.requestFocus());

    var homeActions = robot.lookup("#homeActions").queryAs(Parent.class);
    var plusGoalHome = (Button) homeActions.lookup("#plusGoalButton");
    robot.clickOn(plusGoalHome);

    var guestActions = robot.lookup("#guestActions").queryAs(Parent.class);
    var plusGoalAway = (Button) guestActions.lookup("#plusGoalButton");
    robot.clickOn(plusGoalAway);


    assertThat(homeGoalsLabel).hasText("1");
    assertThat(guestGoalsLabel).hasText("1");
  }

}
