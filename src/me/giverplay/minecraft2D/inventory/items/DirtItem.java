package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class DirtItem extends Item
{
	public DirtItem(Integer amount)
	{
		super("Dirt", Material.DIRT, amount, 8, Tile.TILE_DIRT);
	}
}
