package de.tvneheim.scoreboardfx.viewmodel;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.tvneheim.scoreboardfx.infrastructure.persistence.json.JsonDatabase;
import de.tvneheim.scoreboardfx.model.Game;
import de.tvneheim.scoreboardfx.viewmodel.events.Event;
import de.tvneheim.scoreboardfx.viewmodel.events.EventType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Slf4j
public final class GameState {

  private static final Settings SETTINGS = Settings.defaultSettings().build();

  private static final Game INITIAL_GAME = new Game(SETTINGS.homeTeamName().get(), SETTINGS.guestTeamName().get());

  private static final ObservableList<Event> EVENTS = FXCollections.observableArrayList();

  private static final StopWatch STOP_WATCH = new StopWatch(SETTINGS);

  private static final ObjectProperty<Game> GAME = new SimpleObjectProperty<>(INITIAL_GAME);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

  private static void updateGameState() {
    var current = INITIAL_GAME;
    for (Event event : GameState.getEvents()) {
      current = event.apply(current);
    }
    GAME.setValue(current);
  }

  @SneakyThrows
  public static <E extends Event> void addEvent(E event) {
    log.info("Event added: {}", event.description());
    EVENTS.add(event);
    updateGameState();

    var json = OBJECT_MAPPER.writeValueAsString(event);
    JsonDatabase.INSTANCE.enqueue(json);

  }

  public static void removeEvent(UUID eventId) {
    EVENTS.removeIf(event -> event.getId().equals(eventId));
    updateGameState();
  }

  public static List<Event> getEvents() {
    return EVENTS.stream().toList();
  }

  public static ObservableList<Event> getEventsProperty() {
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

  public static Game getCurrentGame() {
    return GAME.getValue();
  }

  public static boolean isRunning() {
    return STOP_WATCH.getPeriodTimer().isRunning();
  }

  public static boolean isPaused() {
    return STOP_WATCH.getPeriodTimer().isStopped();
  }

  public static Event findLastOfType(EventType type) {
    return EVENTS.stream().filter(event -> event.type() == type).toList().getLast();
  }
}
