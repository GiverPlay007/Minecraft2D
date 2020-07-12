package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class StoneBricksItem extends Item
{
	public StoneBricksItem(Integer amount)
	{
		super("Stone Bricks", Material.STONE_BRICKS, amount, 64, Spritesheet.TILE_STONE_BRICKS);
	}
}
