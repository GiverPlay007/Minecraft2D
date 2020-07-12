package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class SandItem extends Item
{
	public SandItem(Integer amount)
	{
		super("Sand", Material.SAND, amount, 64, Spritesheet.TILE_SAND);
	}
}
