package me.giverplay.minecraft2D.command;

import java.util.HashMap;

import me.giverplay.minecraft2D.command.commands.GamemodeCommand;
import me.giverplay.minecraft2D.command.commands.GiveCommand;
import me.giverplay.minecraft2D.command.commands.RemoveCommand;

public class CommandManager
{
	private HashMap<String, Command> commands = new HashMap<>();
	
	public CommandManager()
	{
		registerCommands();
	}
	
	private void registerCommands()
	{
		commands.put("give", new GiveCommand());
		commands.put("remove", new RemoveCommand());
		commands.put("gamemode", new GamemodeCommand());
	}
	
	public void dispatchCommand(String cmd, String[] args)
	{
		if(!commands.containsKey(cmd))
		{
			System.out.println("Comando " + cmd + " não existe.");
			return;
		}
		
		commands.get(cmd).execute(args);
	}
}