package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.world.Tile;

public class SandTile extends Tile
{
	public SandTile(int x, int y, boolean isFinal)
	{
		super(x, y, true, isFinal, Tile.TILE_SAND);
	}
	
	public SandTile(int x, int y)
	{
		super(x, y, true, false, Tile.TILE_SAND);
	}
	
}
