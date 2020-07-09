package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.world.Tile;

public class GrassItem extends Item
{
	public GrassItem(int amount)
	{
		super("Grass", ItemEnum.GRASS, 1, 8, Tile.TILE_GRASS);
	}
}
