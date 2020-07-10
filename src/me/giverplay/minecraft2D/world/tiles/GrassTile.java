package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class GrassTile extends Tile
{
	public GrassTile(Integer x, Integer y)
	{
		super(Material.GRASS, x, y, true, false, Tile.TILE_GRASS);
	}
	
	public GrassTile(int x, int y, boolean isFinal)
	{
		super(Material.GRASS, x, y, true, isFinal, Tile.TILE_GRASS);
	}
}
