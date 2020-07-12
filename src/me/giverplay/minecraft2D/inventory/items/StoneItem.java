package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class StoneItem extends Item
{
	public StoneItem(Integer amount)
	{
		super("Stone", Material.STONE, amount, 64, Spritesheet.TILE_STONE);
	}
}
