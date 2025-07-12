package de.tvneheim.scoreboardfx.game;

import java.time.Duration;

import de.tvneheim.scoreboardfx.model.TimeStamp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.Iterator;
import java.util.List;

@Log
public class StopWatch {

  private int millis, seconds, minutes;

  private final Iterator<Period> periodIterator;

  private final Timeline timeline;

  @Getter
  private final ObjectProperty<Period> period;
  @Getter
  private final StringProperty time;
  @Getter
  private final BooleanProperty stopped;
  @Getter
  private final ObjectProperty<TimeStamp> timestamp;
  @Getter
  private final SuspensionSlots suspensionsHome;
  @Getter
  private final SuspensionSlots suspensionsGuest;


  public StopWatch(List<Period> periods) {
    this.periodIterator = periods.iterator();
    this.period = new SimpleObjectProperty<>(periodIterator.next());
    log.info("Initialized with period: " + period);

    this.time = new SimpleStringProperty("00:00");
    this.stopped = new SimpleBooleanProperty(true);
    this.timestamp = new SimpleObjectProperty<>(new TimeStamp(millis, seconds, minutes));

    this.timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(1), event -> updateTime()));
    this.timeline.setCycleCount(Timeline.INDEFINITE);
    this.timeline.setAutoReverse(false);

    this.suspensionsHome = new SuspensionSlots();
    this.suspensionsGuest = new SuspensionSlots();
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

    if (getDuration().compareTo(period.get().getEndTime()) >= 0 && periodIterator.hasNext()) {
      period.get().onComplete(this);
      period.setValue(periodIterator.next());
      setDuration(period.get().getDuration());
      log.info("New period is: " + period);
      period.get().onInit(this);
      timestamp.setValue(new TimeStamp(millis, seconds, minutes));
    }

    var text = String.format("%02d:%02d", minutes, seconds);
    time.setValue(text);
    timestamp.setValue(new TimeStamp(millis, seconds, minutes));


    if (getPeriod().get().isTrackPenalties()) {
      suspensionsHome.getSuspensions().forEach(penalty -> penalty.updateTime(getCurrentTime()));
      suspensionsGuest.getSuspensions().forEach(penalty -> penalty.updateTime(getCurrentTime()));
    }
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

  private Duration getDuration() {
    return Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(millis);
  }

  private void setDuration(Duration duration) {
    minutes = duration.toMinutesPart();
    seconds = duration.toSecondsPart();
    millis = duration.toMillisPart();
  }
}
