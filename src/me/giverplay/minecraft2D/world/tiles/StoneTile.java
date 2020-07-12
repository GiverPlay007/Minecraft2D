package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class StoneTile extends Tile
{
	public StoneTile(int x, int y, Boolean isFinal)
	{
		super(Material.STONE, x, y, true, isFinal, Tile.TILE_STONE);
	}
	
	public StoneTile(Integer x, Integer y)
	{
		super(Material.STONE, x, y, true, false, Tile.TILE_STONE);
	}
}
