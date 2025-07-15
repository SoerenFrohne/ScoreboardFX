package de.tvneheim.scoreboardfx.infrastructure.persistence.json;

import lombok.extern.java.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log
public enum JsonDatabase {

  INSTANCE;

  private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

  JsonDatabase() {

    String outputFileName = "output.txt";

    Thread consumerThread = new Thread(new FileConsumer(queue, outputFileName));

    consumerThread.start();

    try {
      consumerThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void enqueue(String string) {
    var added = queue.offer(string);
    if (!added) {
      log.warning("No space available");
    }
  }
}
