package de.tvneheim.scoreboardfx.infrastructure.persistence.json;

import lombok.extern.java.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

@Log
public class FileConsumer implements Runnable {
  private final BlockingQueue<String> queue;
  private final String outputFileName;

  public FileConsumer(BlockingQueue<String> queue, String outputFileName) {
    this.queue = queue;
    this.outputFileName = outputFileName;
  }

  @Override
  public void run() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
      String line;
      while ((line = queue.poll()) != null) {
        writer.write(line);
        writer.newLine();
      }

    } catch (IOException e) {
      log.severe(e.getMessage());
    }
  }
}
