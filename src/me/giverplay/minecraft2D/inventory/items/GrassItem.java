package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.ItemEnum;
import me.giverplay.minecraft2D.world.Tile;

public class GrassItem extends Item
{
	public GrassItem(Integer amount)
	{
		super("Grass", ItemEnum.GRASS, 1, 8, Tile.TILE_GRASS);
	}
}
