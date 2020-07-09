package me.giverplay.minecraft2D.world;

public class FloorTile extends Tile
{
	public FloorTile(int x, int y, boolean top)
	{
		super(x, y, true, top ? Tile.TILE_GRASS : Tile.TILE_DIRT);
	}
}
