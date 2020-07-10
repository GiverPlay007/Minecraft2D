package me.giverplay.minecraft2D.world.tiles;

import java.awt.Graphics;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class AirTile extends Tile
{
	public AirTile(Integer x, Integer y)
	{
		super(Material.AIR, x, y, false, false, null);
	}
	
	public AirTile(int x, int y, boolean isFinal)
	{
		super(Material.AIR, x, y, false, isFinal, null);
	}
	
	@Override
	public void render(Graphics g)
	{
		// Nada :)
	}
}
