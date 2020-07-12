package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class GrassItem extends Item
{
	public GrassItem(Integer amount)
	{
		super("Grass", Material.GRASS, amount, 64, Spritesheet.TILE_GRASS);
	}
}
