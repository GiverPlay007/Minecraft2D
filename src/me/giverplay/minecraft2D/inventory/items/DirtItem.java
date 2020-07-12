package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class DirtItem extends Item
{
	public DirtItem(Integer amount)
	{
		super("Dirt", Material.DIRT, amount, 64, Spritesheet.TILE_DIRT);
	}
}
