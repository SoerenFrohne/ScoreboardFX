package de.tvneheim.scoreboardfx.events;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
public final class EventStore {

  private static final ObservableList<Event> EVENTS = FXCollections.observableArrayList();

  public static void addEvent(Event event) {
    log.info("Event added: " + event.description());
    EVENTS.add(event);
  }

  public static void removeEvent(UUID eventId) {
    EVENTS.removeIf(event -> event.getId().equals(eventId));
  }

  public static List<Event> getEvents() {
    return EVENTS.stream().toList();
  }

  public static ObservableList<Event> getProperty() {
    return EVENTS;
  }
}
