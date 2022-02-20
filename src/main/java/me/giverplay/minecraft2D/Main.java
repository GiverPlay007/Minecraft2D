package me.giverplay.minecraft2D;

import me.giverplay.minecraft2D.game.DiscordPresence;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.sound.Sound;

public class Main {

  public static final DiscordPresence discordRichPresence = new DiscordPresence();

  public static void main(String[] args) {
    try {
      Sprites.init();
      Sound.init();
    } catch (Throwable t) {
      System.out.println("Falha ao carregar assets :(");
      t.printStackTrace();
      System.exit(1);
    }

    discordRichPresence.start();

    Game game = new Game();
    game.start();
  }
}
