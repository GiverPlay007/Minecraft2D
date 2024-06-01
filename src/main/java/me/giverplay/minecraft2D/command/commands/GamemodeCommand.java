package me.giverplay.minecraft2D.command.commands;

import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.game.GameMode;

public class GamemodeCommand extends Command {

  public GamemodeCommand(Game game) {
    super(game, "gamemode");
  }

  @Override
  public void execute(String[] args) {
    if(args.length == 0) {
      System.out.println("Especifique o modo de jogo...");
      return;
    }

    if(!GameMode.isGameMode(args[0])) {
      System.out.println(args[0] + " não é um modo de jogo.");
      return;
    }

    GameMode mode;

    try {
      mode = GameMode.parse(args[0]);
    } catch (IllegalArgumentException e) {
      System.out.println("Esse modo de jogo não existe (" + args[0] + ").");
      return;
    }

    PlayerEntity player = game.getPlayer();

    if(player.getGameMode() == mode) {
      System.out.println("Já está no modo de jogo '" + mode.getDescriptionName() + "'.");
      return;
    }

    player.setGameMode(mode);
    System.out.println("Modo de jogo alterado para " + mode.getDescriptionName());
  }
}
