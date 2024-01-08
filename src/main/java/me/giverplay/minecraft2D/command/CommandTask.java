package me.giverplay.minecraft2D.command;

import me.giverplay.minecraft2D.game.Game;

import java.util.Scanner;

public class CommandTask extends Thread implements Runnable {

  private final Game game;

  public CommandTask(Game game) {
    super("Game Command Thread");
    this.game = game;
  }

  @Override
  public void run() {
    System.out.print("> ");
    Scanner scan = new Scanner(System.in);
    String command;

    while (game.isRunning()) {
      command = scan.nextLine();

      game.getCommandManager().dispatchCommand(command);
      System.out.print("> ");
    }

    scan.close();
  }
}
