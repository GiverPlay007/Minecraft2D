package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.ItemEnum;
import me.giverplay.minecraft2D.world.Tile;

public class StoneItem extends Item
{
	public StoneItem(Integer amount)
	{
		super("Stone", ItemEnum.STONE, amount, 8, Tile.TILE_STONE);
	}
}
