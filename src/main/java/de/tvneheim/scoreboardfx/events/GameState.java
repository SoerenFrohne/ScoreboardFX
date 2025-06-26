package de.tvneheim.scoreboardfx.events;


import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.model.Settings;
import de.tvneheim.scoreboardfx.view.StopWatch;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
public final class GameState {

  private static final ObservableList<Event> EVENTS = FXCollections.observableArrayList();

  private static final Settings SETTINGS = new Settings(
      30,
      3,
      "D:\\TV Neheim 1884\\Scoreboard\\Logos",
      "D:\\TV Neheim 1884\\Scoreboard\\Werbung\\Bilder",
      ""
  );

  private static final StopWatch STOP_WATCH = new StopWatch();

  private static final Game INITIAL_GAME = new Game("HEIM", "GAST", SETTINGS);

  private static final ObjectProperty<Game> GAME = new SimpleObjectProperty<>(
      INITIAL_GAME
  );

  private static void updateGameState() {
    var current = INITIAL_GAME;
    for (Event event : GameState.getEvents()) {
      current = event.apply(current);
    }
    GAME.setValue(current);
  }

  public static void addEvent(Event event) {
    log.info("Event added: " + event.description());
    EVENTS.add(event);
    updateGameState();
  }

  public static void removeEvent(UUID eventId) {
    EVENTS.removeIf(event -> event.getId().equals(eventId));
    updateGameState();
  }

  public static List<Event> getEvents() {
    return EVENTS.stream().toList();
  }

  public static ObservableList<Event> getProperty() {
    return EVENTS;
  }

  public static ObjectProperty<Game> getGame() {
    return GAME;
  }

  public static StopWatch getStopWatch() {
    return STOP_WATCH;
  }

  public static Settings getSettings() {
    return SETTINGS;
  }

  public static boolean isRunning() {
    return STOP_WATCH.isRunning();
  }

  public static boolean isPaused() {
    return STOP_WATCH.isPaused();
  }
}
