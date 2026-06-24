package de.tvneheim.scoreboardfx.infrastructure.sound;

import javafx.scene.media.AudioClip;

import java.util.Objects;

public final class SoundBoard {

  private static final String HORN_LONG_PATH = "/de/tvneheim/scoreboardfx/sfx/horn-long.wav";
  private static final String HORN_SHORT_PATH = "/de/tvneheim/scoreboardfx/sfx/horn-short.wav";

  private static final AudioClip HORN_LONG = loadAudioClip(HORN_LONG_PATH);
  private static final AudioClip HORN_SHORT = loadAudioClip(HORN_SHORT_PATH);


  private SoundBoard() {
    // Vorwärmen, damit beim ersten Laden keine Verzögerungen entstehen
    HORN_SHORT.setVolume(0);
    HORN_SHORT.play();
    HORN_SHORT.setVolume(100);

    HORN_LONG.setVolume(0);
    HORN_LONG.play();
    HORN_LONG.setVolume(100);
  }

  public static void honkShort() {
    HORN_SHORT.play();
  }

  public static void honkLong() {
    HORN_LONG.play();
  }

  private static AudioClip loadAudioClip(String path) {
    return new AudioClip(Objects.requireNonNull(SoundBoard.class.getResource(path)).toExternalForm()
    );
  }

}
