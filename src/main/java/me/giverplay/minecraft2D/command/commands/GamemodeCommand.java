package me.giverplay.minecraft2D.command.commands;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.command.Command;
import me.giverplay.minecraft2D.game.GameMode;

public class GamemodeCommand extends Command
{
	public GamemodeCommand()
	{	
		super("gamemode");
	}
	
	@Override
	public void execute(String[] args)
	{
		if(args.length == 0)
		{
			System.out.println("Especifique o modo de jogo...");
			return;
		}
		
		GameMode mode;
		
		try
		{
			mode = GameMode.valueOf(args[0].toUpperCase());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Gamemode inválido: " + args[0]);
			return;
		}
		
		Game.getGame().getPlayer().setGamemode(mode);
		System.out.println("Modo de jogo alterado para " + mode.name());
	}
}
