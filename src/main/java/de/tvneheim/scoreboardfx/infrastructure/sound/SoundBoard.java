package de.tvneheim.scoreboardfx.infrastructure.sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;

public final class SoundBoard {

  private static final String HORN_LONG_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-long.wav";
  private static final String HORN_MID_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-mid.wav";
  private static final String HORN_SHORT_PATH =
      "/de/tvneheim/scoreboardfx/sfx/horn-short.wav";

  private static final Clip HORN_LONG = loadClip(HORN_LONG_PATH);
  private static final Clip HORN_MID = loadClip(HORN_MID_PATH);
  private static final Clip HORN_SHORT = loadClip(HORN_SHORT_PATH);

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

  private static void play(Clip clip) {
    if (clip.isRunning()) {
      clip.stop();
    }

    clip.setFramePosition(0);
    clip.start();
  }

  private static Clip loadClip(String path) {
    try {
      var inputStream = SoundBoard.class.getResourceAsStream(path);

      if (inputStream == null) {
        throw new IllegalArgumentException("Sound resource not found: " + path);
      }

      try (
          var bufferedInputStream = new BufferedInputStream(inputStream);
          var audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream)
      ) {
        var clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
      }

    } catch (Exception e) {
      throw new IllegalStateException("Could not load sound: " + path, e);
    }
  }
}