package de.tvneheim.scoreboardfx.viewmodel;

import java.time.Duration;

public interface TickListener {
  void onTick(Duration delta);
}
