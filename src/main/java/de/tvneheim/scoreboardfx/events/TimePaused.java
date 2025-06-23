package de.tvneheim.scoreboardfx.events;

import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.utils.FormatterUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimePaused extends Event {

  private final int millis, seconds, minutes;

  @Override
  public String description() {
    return "Zeit angehalten: " + FormatterUtils.time(minutes, seconds);
  }

  @Override
  public Game apply(Game current) {
    var time = current.time();
    var newTime = time
        .withPaused(true)
        .withMillis(millis)
        .withSeconds(seconds)
        .withMinutes(minutes);
    return current.withTime(newTime);
  }
}
