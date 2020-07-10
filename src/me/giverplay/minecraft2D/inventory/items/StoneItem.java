package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class StoneItem extends Item
{
	public StoneItem(Integer amount)
	{
		super("Stone", Material.STONE, amount, 64, Tile.TILE_STONE);
	}
}
