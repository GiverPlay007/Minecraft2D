package me.giverplay.minecraft2D.command;

import java.util.Arrays;
import java.util.Scanner;

import me.giverplay.minecraft2D.game.Game;

public class CommandTask extends Thread implements Runnable
{
	private final Game game;
	
	public CommandTask(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void run()
	{
		System.out.print("> ");
		Scanner scan = new Scanner(System.in);
		String command;
		
		while(game.isRunning())
		{
			command = scan.nextLine();
			
			game.getCommandManager().dispatchCommand(command);
			System.out.print("> ");
		}
		
		scan.close();
	}
}
