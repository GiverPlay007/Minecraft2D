package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class BricksItem extends Item
{
	public BricksItem(Integer amount)
	{
		super("Bricks", Material.BRICKS, amount, 64, Tile.TILE_BRICKS);
	}
}
