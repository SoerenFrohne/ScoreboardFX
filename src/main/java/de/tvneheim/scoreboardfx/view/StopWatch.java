package de.tvneheim.scoreboardfx.view;

import de.tvneheim.scoreboardfx.model.TimeStamp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.util.Duration;
import lombok.Getter;

public class StopWatch {

  private int millis, seconds, minutes;
  private final Timeline timeline;

  @Getter
  private final StringProperty time = new SimpleStringProperty("00:00");
  @Getter
  private final BooleanProperty stopped = new SimpleBooleanProperty(true);
  @Getter
  private final ObjectProperty<TimeStamp> timestamp = new SimpleObjectProperty<>(new TimeStamp(millis, seconds, minutes));


  public StopWatch() {
    timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> updateTime()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.setAutoReverse(false);
  }

  public void start() {
    if (isPaused()) {
      stopped.setValue(false);
      timeline.play();
    }
  }

  public void pause() {
    if (isRunning()) {
      stopped.setValue(true);
      timeline.stop();
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
    time.setValue(text);
    timestamp.setValue(new TimeStamp(millis, seconds, minutes));
  }

  public boolean isRunning() {
    return !stopped.getValue();
  }

  public boolean isPaused() {
    return stopped.getValue();
  }

  public TimeStamp getCurrentTime() {
    return timestamp.getValue();
  }

  public void reset() {
    timeline.stop();
    millis = 0;
    seconds = 0;
    minutes = 0;
    time.setValue(String.format("%02d:%02d", minutes, seconds));
    stopped.setValue(true);
  }
}
