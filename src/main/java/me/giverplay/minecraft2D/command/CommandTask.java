package me.giverplay.minecraft2D.command;

import java.util.Arrays;
import java.util.Scanner;

import me.giverplay.minecraft2D.game.Game;

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
		
		try
		{
			game.getCommandManager().dispatchCommand(data[0], args);
		}
		catch(Exception e)
		{
			System.out.println(" ");
			System.out.println("Falha ao executar o comando '" + data[0] + "'.");
			System.out.println("Causa: " + e.getCause());
			System.out.println("Erro: " + e.getMessage());
			System.out.println(" ");
		}
	}
}
