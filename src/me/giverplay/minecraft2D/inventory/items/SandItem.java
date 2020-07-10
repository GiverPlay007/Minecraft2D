package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.ItemEnum;
import me.giverplay.minecraft2D.world.Tile;

public class SandItem extends Item
{
	public SandItem(Integer amount)
	{
		super("Sand", ItemEnum.SAND, amount, 8, Tile.TILE_SAND);
	}
}
