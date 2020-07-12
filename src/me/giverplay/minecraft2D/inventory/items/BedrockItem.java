package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;

public class BedrockItem extends Item
{
	public BedrockItem(Integer amount)
	{
		super("Bedrock", Material.BEDROCK, amount, 64, Spritesheet.TILE_BEDROCK);
	}
}
