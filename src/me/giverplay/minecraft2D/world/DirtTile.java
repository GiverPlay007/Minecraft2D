package me.giverplay.minecraft2D.world;

public class DirtTile extends Tile
{
	public DirtTile(int x, int y, boolean isFinal)
	{
		super(x, y, true, isFinal, Tile.TILE_DIRT);
	}
	
	public DirtTile(int x, int y)
	{
		super(x, y, true, false, Tile.TILE_DIRT);
	}
}
