package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class WoodTile extends Tile
{
	public WoodTile(int x, int y, Boolean isFinal)
	{
		super(Material.WOOD, x, y, true, isFinal, Spritesheet.TILE_WOOD);
	}
	
	public WoodTile(Integer x, Integer y)
	{
		super(Material.WOOD, x, y, true, false, Spritesheet.TILE_WOOD);
	}
}
