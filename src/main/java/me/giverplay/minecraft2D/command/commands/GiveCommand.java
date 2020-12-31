package me.giverplay.minecraft2D.command.commands;

import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.world.Material;

public class GiveCommand extends Command
{
	private Game game;
	
	public GiveCommand()
	{
		super("give");
		
		//game = Game.getGame();
	}
	
	@Override
	public void execute(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("Utilize 'give <item> <quantidade>'");
			return;
		}
		
		int amount;
		
		try
		{
			amount = Math.abs(Integer.parseInt(args[1]));
		}
		catch(NumberFormatException e)
		{
			System.out.println("A quantidade deve ser em números...");
			return;
		}
		
		Item item;
		
		try
		{
		  item = new Item(Material.valueOf(args[0].toUpperCase()), amount);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("A quantidade não pode ser maior que o limite do stack ou o item não existe");
			return;
		}
		
		if(!game.getPlayer().getInventory().addItem(item))
		{
			System.out.println("Inventário lotado...");
			return;
		}
		
		System.out.println("Item adicionado com sucesso: " + args[1] + "x " + item.getType().getName());
	}
}
