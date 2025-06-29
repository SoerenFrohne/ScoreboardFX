package de.tvneheim.scoreboardfx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public record Player(
    IntegerProperty number,
    StringProperty firstName,
    StringProperty secondName,
    IntegerProperty goals
    //TODO: track penalties
) {

  public Player(int number) {
    this(
        new SimpleIntegerProperty(number),
        new SimpleStringProperty(),
        new SimpleStringProperty(),
        new SimpleIntegerProperty(0)
    );
  }
}
