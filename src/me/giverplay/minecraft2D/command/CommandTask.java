package me.giverplay.minecraft2D.command;

import java.util.Arrays;
import java.util.Scanner;

import me.giverplay.minecraft2D.Game;

public class CommandTask extends Thread implements Runnable
{
	private Game game;
	
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
			
			dispatchCommand(command);
			System.out.print("> ");
		}
		
		scan.close();
	}
	
	public void dispatchCommand(String command)
	{
		String[] data = command.split(" ");
		String[] args = Arrays.copyOfRange(data, 1, data.length);
		
		game.getCommandManager().dispatchCommand(data[0], args);
	}
}
