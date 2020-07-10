package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.world.Tile;

public class StoneTile extends Tile
{
	public StoneTile(int x, int y, boolean isFinal)
	{
		super(x, y, true, isFinal, Tile.TILE_STONE);
	}
	
	public StoneTile(int x, int y)
	{
		super(x, y, true, false, Tile.TILE_STONE);
	}
}
