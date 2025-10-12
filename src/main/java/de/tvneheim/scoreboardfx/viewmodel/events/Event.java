package de.tvneheim.scoreboardfx.viewmodel.events;

import de.tvneheim.scoreboardfx.model.Game;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class Event {

  private UUID id = UUID.randomUUID();
  
  private LocalDateTime created = LocalDateTime.now();

  public abstract EventType type();

  public abstract String description();

  public abstract Game apply(Game current);
}
