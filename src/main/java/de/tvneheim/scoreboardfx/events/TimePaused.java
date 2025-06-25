package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.utils.FormatterUtils;
import de.tvneheim.scoreboardfx.view.TimeStamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimePaused extends Event {

  private final TimeStamp timeStamp;

  @Override
  public String description() {
    return "Zeit angehalten: " + FormatterUtils.time(timeStamp.minutes(), timeStamp.seconds());
  }

  @Override
  public Game apply(Game current) {
    var time = current.time();
    var newTime = time
        .withPaused(true)
        .withMillis(timeStamp.millis())
        .withSeconds(timeStamp.seconds())
        .withMinutes(timeStamp.minutes());
    return current.withTime(newTime);
  }
}
