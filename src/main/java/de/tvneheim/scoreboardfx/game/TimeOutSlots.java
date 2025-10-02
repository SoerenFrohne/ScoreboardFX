package de.tvneheim.scoreboardfx.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class TimeOutSlots {

  private final int maxNumberOfTimeOutsPerPeriod;
  private final int maxNumberOfTimeOutsPerGame;

  @Getter
  private final ObservableList<TeamTimeOut> timeOuts;

  public TimeOutSlots(int maxNumberOfTimeOutsPerPeriod, int maxNumberOfTimeOutsPerGame) {
    this.maxNumberOfTimeOutsPerPeriod = maxNumberOfTimeOutsPerPeriod;
    this.maxNumberOfTimeOutsPerGame = maxNumberOfTimeOutsPerGame;
    this.timeOuts = FXCollections.observableArrayList();
  }

  public void add(TeamTimeOut timeOut) {
    if (timeOuts.size() <= maxNumberOfTimeOutsPerGame) {
      timeOuts.add(timeOut);
    }
  }

  public void remove(TeamTimeOut timeOut) {
    timeOuts.remove(timeOut);
  }


}
