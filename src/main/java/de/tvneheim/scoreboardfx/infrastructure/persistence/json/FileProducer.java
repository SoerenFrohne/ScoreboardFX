package de.tvneheim.scoreboardfx.infrastructure.persistence.json;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

@Log
public class FileProducer implements Runnable {

  private final BlockingQueue<String> queue;

  private final String inputFileName;

  public FileProducer(BlockingQueue<String> queue, String inputFileName) {
    this.queue = queue;
    this.inputFileName = inputFileName;
  }

  @Override
  public void run() {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        queue.offer(line);
      }
    } catch (IOException e) {
      log.severe(e.getMessage());
    }
  }
}
