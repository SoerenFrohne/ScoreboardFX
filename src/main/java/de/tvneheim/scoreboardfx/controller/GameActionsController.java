package de.tvneheim.scoreboardfx.controller;

import de.tvneheim.scoreboardfx.GameService;
import de.tvneheim.scoreboardfx.model.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lombok.extern.java.Log;

@Log
public class GameActionsController {

    @FXML
    private Label time;

    @FXML
    private Button startStopButton;

    private boolean stopped;
    private int millis, seconds, minutes;
    private final Timeline timeline;

    public GameActionsController() {
        stopped = true;
        timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
    }

    @FXML
    protected void toggleStartStop() {
        stopped = !stopped;
        toggleStartStopText();

        if (stopped) {
            GameService.stopTime(minutes, seconds, millis);
            timeline.stop();
        } else {
            GameService.startGame();
            timeline.play();
        }
    }

    private void updateTime() {

        millis++;

        if (millis == 1000) {
            seconds++;
            millis = 0;
        }

        if (seconds == 60) {
            minutes++;
            seconds = 0;
        }

        var text = String.format("%02d:%02d", minutes, seconds);
        //log.info(text);
        time.setText(text);
    }

    private void toggleStartStopText() {
        startStopButton.setText(stopped ? "Start" : "Pause");
    }
}
