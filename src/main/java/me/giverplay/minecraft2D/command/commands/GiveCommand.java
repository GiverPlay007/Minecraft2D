package me.giverplay.minecraft2D.command.commands;

import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.world.Material;

public class GiveCommand extends Command {

  public GiveCommand(Game game) {
    super(game, "give");
  }

  @Override
  public void execute(String[] args) {
    if(args.length < 2) {
      System.out.println("Utilize 'give <item> <quantidade>'");
      return;
    }

    int amount;

    try {
      amount = Math.abs(Integer.parseInt(args[1]));
    } catch (NumberFormatException e) {
      System.out.println("A quantidade deve ser um número...");
      return;
    }

    if(!Material.isMaterial(args[0])) {
      System.out.println(args[0] + "não é um material válido!");
      return;
    }

    Material type = Material.parse(args[0]);
    Item item;

    try {
      item = new Item(type, amount);
    } catch (IllegalArgumentException e) {
      System.out.println("Não é possível criar " + type.getName() + " com " + amount + " de quantia!");
      return;
    }

    if(!game.getPlayer().getInventory().addItem(item)) {
      System.out.println("Inventário lotado...");
      return;
    }

    System.out.println("Item adicionado com sucesso: " + amount + "x " + type.getName());
  }
}
