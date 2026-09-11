package de.tvneheim.scoreboardfx.infrastructure.sound;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class SoundBoard {

  private static final String HORN_LONG_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-long.wav";
  private static final String HORN_MID_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-mid.wav";
  private static final String HORN_SHORT_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-short.wav";

  /*
   * Einheitliches Ausgabeformat:
   *
   * 48 kHz
   * 16 Bit
   * Stereo
   * signed
   * little endian
   */
  private static final AudioFormat OUTPUT_FORMAT =
      new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED,
          48_000,
          16,
          2,
          4,
          48_000,
          false
      );

  /*
   * 10 ms Audio pro Schreibvorgang:
   *
   * 48.000 Frames/s
   * => 480 Frames / 10 ms
   * => 480 * 4 Byte = 1920 Byte
   */
  private static final int BUFFER_SIZE = 480 * OUTPUT_FORMAT.getFrameSize();

  private static final byte[] HORN_LONG = loadSound(HORN_LONG_PATH);
  private static final byte[] HORN_MID = loadSound(HORN_MID_PATH);
  private static final byte[] HORN_SHORT = loadSound(HORN_SHORT_PATH);

  private static final Object LOCK = new Object();

  private static byte[] currentSound;
  private static int currentPosition;

  static {
    startAudioThread();
  }

  private SoundBoard() {
  }

  public static void honkShort() {
    play(HORN_SHORT);
  }

  public static void honkMid() {
    play(HORN_MID);
  }

  public static void honkLong() {
    play(HORN_LONG);
  }

  private static void play(byte[] sound) {
    synchronized (LOCK) {
      currentSound = sound;
      currentPosition = 0;
    }
  }

  private static void startAudioThread() {
    Thread thread = new Thread(() -> {

      try {
        SourceDataLine line =
            AudioSystem.getSourceDataLine(OUTPUT_FORMAT);

        /*
         * Relativ kleiner interner Buffer.
         *
         * Zu große Buffer können zusätzliche Latenz verursachen.
         */
        int lineBufferSize = BUFFER_SIZE * 4;

        line.open(OUTPUT_FORMAT, lineBufferSize);
        line.start();

        byte[] outputBuffer = new byte[BUFFER_SIZE];

        while (!Thread.currentThread().isInterrupted()) {

          /*
           * Standardmäßig Stille.
           */
          java.util.Arrays.fill(outputBuffer, (byte) 0);

          synchronized (LOCK) {

            if (currentSound != null) {

              int remaining =
                  currentSound.length - currentPosition;

              int bytesToCopy =
                  Math.min(
                      remaining,
                      outputBuffer.length
                  );

              System.arraycopy(
                  currentSound,
                  currentPosition,
                  outputBuffer,
                  0,
                  bytesToCopy
              );

              currentPosition += bytesToCopy;

              if (currentPosition >= currentSound.length) {
                currentSound = null;
                currentPosition = 0;
              }
            }
          }

          /*
           * Auch wenn kein Sound läuft, werden Nullen geschrieben.
           *
           * Dadurch bleibt der Windows-Audio-Endpunkt aktiv.
           */
          line.write(
              outputBuffer,
              0,
              outputBuffer.length
          );
        }

        line.drain();
        line.stop();
        line.close();

      } catch (LineUnavailableException e) {
        throw new IllegalStateException(
            "Could not initialize audio output",
            e
        );
      }
    });

    thread.setName("scoreboard-audio");
    thread.setDaemon(true);
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.start();
  }

  private static byte[] loadSound(String path) {

    try (
        InputStream resourceStream =
            SoundBoard.class.getResourceAsStream(path)
    ) {

      if (resourceStream == null) {
        throw new IllegalArgumentException(
            "Sound resource not found: " + path
        );
      }

      try (
          AudioInputStream originalStream =
              AudioSystem.getAudioInputStream(resourceStream);

          AudioInputStream convertedStream =
              AudioSystem.getAudioInputStream(
                  OUTPUT_FORMAT,
                  originalStream
              );

          ByteArrayOutputStream output =
              new ByteArrayOutputStream()
      ) {

        byte[] buffer = new byte[8192];

        int read;

        while ((read = convertedStream.read(buffer)) != -1) {
          output.write(buffer, 0, read);
        }

        return output.toByteArray();
      }

    } catch (
        UnsupportedAudioFileException |
        IOException e
    ) {
      throw new IllegalStateException(
          "Could not load sound: " + path,
          e
      );
    }
  }
}