package me.giverplay.minecraft2D.command.commands;

import com.sun.xml.internal.ws.util.StringUtils;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.inventory.Item;

public class GiveCommand extends Command
{
	private Game game;
	
	public GiveCommand()
	{
		super("give");
		
		game = Game.getGame();
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
		  item = Item.forName(args[0], amount);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("A quantidade não pode ser maior que o limite do stack");
			return;
		}
		
		if(item == null)
		{
			System.out.println("Item " + args[0] + " não existe ou atingiu o limite do stack...");
			return;
		}
		
		if(!game.getPlayer().getInventory().addItem(item))
		{
			System.out.println("Inventário lotado...");
			return;
		}
		
		System.out.println("Item adicionado com sucesso: " + args[1] + "x " + StringUtils.capitalize(args[0]));
	}
}