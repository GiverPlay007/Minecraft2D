package me.giverplay.minecraft2D.command;

import com.sun.xml.internal.ws.util.StringUtils;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.Material;

public class RemoveCommand extends Command
{
	public RemoveCommand()
	{
		super("remove");
	}
	
	@Override
	public void execute(String[] args)
	{
		Inventory inv = Game.getGame().getPlayer().getInventory();
		
		if(args.length == 0)
		{
			System.out.println("Utilize: remove <type/slot> [amount]");	
			return;
		}
		
		int slot = 1;
		boolean id = false;
		
		try
		{
			slot = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e)
		{
			id = true;
		}
		
		if(!id)
		{
			try
			{
				inv.removeItem(slot);
				System.out.println("Itens removidos do slot " + slot);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
			
			return;
		}
		
		if(args.length < 2)
		{
			System.out.println("Utilize: remove <type/slot> [amount]");
			return;
		}
		
		Material mat = Material.valueOf(args[0].toUpperCase());
		
		if(mat == null)
		{
			System.out.println("Item " + args[0] + " não existe");
			return;
		}
		
		int amount;
		
		try
		{
			amount = Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("A quantidade deve ser número inteiro...");
			return;
		}
		
		try
		{
			inv.removeItem(mat, amount);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Itens removidos: " + amount + "x " + StringUtils.capitalize(args[0]));
	}
}
