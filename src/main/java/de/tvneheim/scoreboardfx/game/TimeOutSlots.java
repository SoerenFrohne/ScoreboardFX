package de.tvneheim.scoreboardfx.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class TimeOutSlots {

  private final int maxNumberOfTimeOuts;

  @Getter
  private final ObservableList<TeamTimeOut> suspensions;

  public TimeOutSlots(int maxNumberOfTimeOuts) {
    this.maxNumberOfTimeOuts = maxNumberOfTimeOuts;
    this.suspensions = FXCollections.observableArrayList();
  }

  public void add(TeamTimeOut timeOut) {
    if (suspensions.size() <= maxNumberOfTimeOuts) {
      suspensions.add(timeOut);
    }
  }

  public void remove(TeamTimeOut timeOut) {
    suspensions.remove(timeOut);
  }


}
