package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class WoodTile extends Tile
{
	public WoodTile(int x, int y, boolean isFinal)
	{
		super(Material.WOOD, x, y, true, isFinal, Tile.TILE_WOOD);
	}
	
	public WoodTile(Integer x, Integer y)
	{
		super(Material.WOOD, x, y, true, false, Tile.TILE_WOOD);
	}
}
