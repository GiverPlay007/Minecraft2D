package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class AirItem extends Item
{
	public AirItem(Integer amount)
	{
		super("Air", Material.AIR, amount, 1, null);
	}
}
