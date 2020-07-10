package me.giverplay.minecraft2D.world.tiles;

import java.awt.Graphics;

import me.giverplay.minecraft2D.world.Tile;

public class AirTile extends Tile
{
	public AirTile(int x, int y)
	{
		super(x, y, false, false, null);
	}
	
	public AirTile(int x, int y, boolean isFinal)
	{
		super(x, y, false, isFinal, null);
	}
	
	@Override
	public void render(Graphics g)
	{
		// Nada :)
	}
}
