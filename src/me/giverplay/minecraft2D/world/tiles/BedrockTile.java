package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class BedrockTile extends Tile
{
	public BedrockTile(int x, int y, Boolean isFinal)
	{
		super(Material.BEDROCK, x, y, true, isFinal, Tile.TILE_BEDROCK);
	}
	
	public BedrockTile(Integer x, Integer y)
	{
		super(Material.BEDROCK, x, y, true, false, Tile.TILE_BEDROCK);
	}
}
