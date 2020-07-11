package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class StoneBricksItem extends Item
{
	public StoneBricksItem(Integer amount)
	{
		super("Stone Bricks", Material.STONE_BRICKS, amount, 64, Tile.TILE_STONE_BRICKS);
	}
}
