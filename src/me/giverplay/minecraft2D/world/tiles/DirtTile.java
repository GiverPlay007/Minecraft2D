package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class DirtTile extends Tile
{
	public DirtTile(int x, int y, boolean isFinal)
	{
		super(Material.DIRT, x, y, true, isFinal, Tile.TILE_DIRT);
	}
	
	public DirtTile(Integer x, Integer y)
	{
		super(Material.DIRT, x, y, true, false, Tile.TILE_DIRT);
	}
}
