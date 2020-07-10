package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.ItemEnum;
import me.giverplay.minecraft2D.world.Tile;

public class DirtItem extends Item
{
	public DirtItem(Integer amount)
	{
		super("Dirt", ItemEnum.DIRT, amount, 8, Tile.TILE_DIRT);
	}
}
