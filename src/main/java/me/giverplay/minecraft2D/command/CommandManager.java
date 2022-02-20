package me.giverplay.minecraft2D.command;

import me.giverplay.minecraft2D.command.commands.ClearCommand;
import me.giverplay.minecraft2D.command.commands.GamemodeCommand;
import me.giverplay.minecraft2D.command.commands.GiveCommand;
import me.giverplay.minecraft2D.game.Game;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {

  private final HashMap<String, Command> commands = new HashMap<>();
  private final Game game;

  public CommandManager(Game game) {
    this.game = game;
    registerCommands();
  }

  private void registerCommands() {
    commands.put("give", new GiveCommand(game));
    commands.put("remove", new ClearCommand(game));
    commands.put("gamemode", new GamemodeCommand(game));
  }

  public void runCommand(String cmd, String[] args) {
    if(!commands.containsKey(cmd)) {
      throw new IllegalArgumentException("Comando " + cmd + " não existe.");
    }

    commands.get(cmd).execute(args);
  }

  public void dispatchCommand(String command) {
    String[] args = command.split(" ");

    if(args.length == 0)
      return;

    String cmd = args[0].toLowerCase();
    args = Arrays.copyOfRange(args, 1, args.length);

    try {
      runCommand(cmd, args);
    } catch (Exception e) {
      System.out.println(" ");
      System.out.println("Falha ao executar o comando '" + cmd + "'.");
      System.out.println("Causa: " + e.getCause());
      System.out.println("Erro: " + e.getMessage());
      System.out.println(" ");
      e.printStackTrace();
    }
  }
}
