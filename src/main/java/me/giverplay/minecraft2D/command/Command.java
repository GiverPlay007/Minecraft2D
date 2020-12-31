package me.giverplay.minecraft2D.command;

import me.giverplay.minecraft2D.game.Game;

public abstract class Command
{
	private final String name;
	protected final Game game;

	public Command(Game game, String name)
	{
		this.game = game;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public abstract void execute(String[] args);
}
