package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.utils.FormatterUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimePaused extends Event {

  private final Duration timeStamp;

  @Override
  public EventType type() {
    return EventType.TIME_PAUSED;
  }

  @Override
  public String description() {
    return "Zeit angehalten: " + FormatterUtils.time(timeStamp);
  }

  @Override
  public Game apply(Game current) {
    var time = current.time();
    var newTime = time
        .withPaused(true)
        .withElapsedTime(timeStamp);
    return current.withTime(newTime);
  }
}
