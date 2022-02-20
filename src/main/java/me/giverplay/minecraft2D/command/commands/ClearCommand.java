package me.giverplay.minecraft2D.command.commands;

import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.world.Material;

public class ClearCommand extends Command {

  public ClearCommand(Game game) {
    super(game, "remove");
  }

  @Override
  public void execute(String[] args) {
    if(args.length == 0) {
      System.out.println("Utilize: remove <type | all> [amount]");
      return;
    }

    Inventory inventory = game.getPlayer().getInventory();

    if(args[0].equalsIgnoreCase("all")) {
      inventory.clear();
      System.out.println("Inventário limpo!");
      return;
    }

    if(!Material.isMaterial(args[0])) {
      System.out.println("Item " + args[0] + " não existe!");
      return;
    }

    Material type = Material.parse(args[0]);

    if(args.length == 1) {
      inventory.removeItem(type);
      System.out.println("Todos os itens do tipo " + type.getName() + " foram removidos do inventário!");
      return;
    }

    int amount;

    try {
      amount = Math.abs(Integer.parseInt(args[1]));
    } catch (NumberFormatException e) {
      System.out.println(args[1] + " não é um número inteiro válido.");
      return;
    }

    inventory.removeItem(type, amount);
    System.out.println("Itens removidos: " + amount + "x " + type.getName());
  }
}
