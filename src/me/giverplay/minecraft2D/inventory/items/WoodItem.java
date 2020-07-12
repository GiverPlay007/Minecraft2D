package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class WoodItem extends Item
{
	public WoodItem(Integer amount)
	{
		super("Wood", Material.WOOD, amount, 64, Spritesheet.TILE_WOOD);
	}
}
