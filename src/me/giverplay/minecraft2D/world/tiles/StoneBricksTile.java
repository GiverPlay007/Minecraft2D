package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class StoneBricksTile extends Tile
{
	public StoneBricksTile(int x, int y, Boolean isFinal)
	{
		super(Material.STONE_BRICKS, x, y, true, isFinal, Tile.TILE_STONE_BRICKS);
	}
	
	public StoneBricksTile(Integer x, Integer y)
	{
		super(Material.STONE_BRICKS, x, y, true, false, Tile.TILE_STONE_BRICKS);
	}
}
