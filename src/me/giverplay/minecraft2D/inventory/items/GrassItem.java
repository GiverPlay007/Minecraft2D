package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class GrassItem extends Item
{
	public GrassItem(Integer amount)
	{
		super("Grass", Material.GRASS, amount, 64, Tile.TILE_GRASS);
	}
}
