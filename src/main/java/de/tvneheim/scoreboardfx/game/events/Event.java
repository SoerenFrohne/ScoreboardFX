package de.tvneheim.scoreboardfx.game.events;

import de.tvneheim.scoreboardfx.model.Game;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class Event {

  private UUID id = UUID.randomUUID();
  
  private LocalDateTime created = LocalDateTime.now();

  public abstract String description();

  public abstract Game apply(Game current);
}
