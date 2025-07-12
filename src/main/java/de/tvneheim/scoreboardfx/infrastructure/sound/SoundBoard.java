package de.tvneheim.scoreboardfx.infrastructure.sound;

import javafx.scene.media.AudioClip;

public final class SoundBoard {

  private static final String HORN_LONG = "/de/tvneheim/scoreboardfx/sfx/horn-long.wav";
  private static final String HORN_SHORT = "/de/tvneheim/scoreboardfx/sfx/horn-short.wav";

  private SoundBoard() {
  }

  public static void honkShort() {
    play(HORN_SHORT);
  }

  public static void honkLong() {
    play(HORN_LONG);
  }

  private static void play(String sound) {
    var sfx = SoundBoard.class.getResource(sound);
    assert sfx != null;
    AudioClip honk = new AudioClip(sfx.toExternalForm());
    honk.play();
  }


}
