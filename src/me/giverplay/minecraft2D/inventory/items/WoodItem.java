package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class WoodItem extends Item
{
	public WoodItem(Integer amount)
	{
		super("Wood", Material.WOOD, amount, 64, Tile.TILE_WOOD);
	}
}
