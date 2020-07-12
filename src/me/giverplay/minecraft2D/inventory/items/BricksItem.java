package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class BricksItem extends Item
{
	public BricksItem(Integer amount)
	{
		super("Bricks", Material.BRICKS, amount, 64, Spritesheet.TILE_BRICKS);
	}
}
