package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.world.Tile;

public class GrassTile extends Tile
{
	public GrassTile(int x, int y)
	{
		super(x, y, true, false, Tile.TILE_GRASS);
	}
	
	public GrassTile(int x, int y, boolean isFinal)
	{
		super(x, y, true, isFinal, Tile.TILE_GRASS);
	}
}
