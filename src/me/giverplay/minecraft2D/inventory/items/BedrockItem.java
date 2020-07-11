package me.giverplay.minecraft2D.inventory.items;

import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class BedrockItem extends Item
{
	public BedrockItem(Integer amount)
	{
		super("Bedrock", Material.BEDROCK, amount, 64, Tile.TILE_BEDROCK);
	}
}