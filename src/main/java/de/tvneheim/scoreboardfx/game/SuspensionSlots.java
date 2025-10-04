package de.tvneheim.scoreboardfx.game;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class SuspensionSlots implements Iterable<SuspensionTimer> {

  private static final int SLOTS = 4;

  private final ObservableList<SuspensionTimer> suspensions = FXCollections.observableArrayList(null, null, null, null);

  public void add(SuspensionTimer suspension) {
    var freeIndex = findFirstFreeIndex();
    suspensions.add(freeIndex, suspension);
    suspensions.remove(freeIndex + 1);
  }

  public void remove(SuspensionTimer suspension) {
    var index = suspensions.indexOf(suspension);
    suspensions.remove(suspension);
    suspensions.add(index, null);
  }

  public void addListener(ListChangeListener<? super SuspensionTimer> changeListener) {
    suspensions.addListener(changeListener);
  }

  public void removeListener(ListChangeListener<? super SuspensionTimer> changeListener) {
    suspensions.removeListener(changeListener);
  }

  private int findFirstFreeIndex() {
    for (int i = 0; i < SLOTS; i++) {
      if (suspensions.get(i) == null) {
        return i;
      }
    }
    throw new RuntimeException("All penalty slots are already in use.");
  }

  /**
   * @return Suspensions as plain list without null values
   */
  public List<SuspensionTimer> getSuspensions() {
    return suspensions.stream().filter(Objects::nonNull).toList();
  }


  @Override
  @NonNull
  public Iterator<SuspensionTimer> iterator() {
    return suspensions.iterator();
  }

  @Override
  public void forEach(Consumer<? super SuspensionTimer> action) {
    suspensions.forEach(action);
  }

  @Override
  public Spliterator<SuspensionTimer> spliterator() {
    return suspensions.spliterator();
  }
}
