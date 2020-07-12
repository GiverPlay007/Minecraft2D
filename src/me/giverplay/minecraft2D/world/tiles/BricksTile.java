package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class BricksTile extends Tile
{
	public BricksTile(int x, int y, Boolean isFinal)
	{
		super(Material.BRICKS, x, y, true, isFinal, Tile.TILE_BRICKS);
	}
	
	public BricksTile(Integer x, Integer y)
	{
		super(Material.BRICKS, x, y, true, false, Tile.TILE_BRICKS);
	}
}
