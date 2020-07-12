package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class DirtTile extends Tile
{
	public DirtTile(int x, int y, Boolean isFinal)
	{
		super(Material.DIRT, x, y, true, isFinal, Spritesheet.TILE_DIRT);
	}
	
	public DirtTile(Integer x, Integer y)
	{
		super(Material.DIRT, x, y, true, false, Spritesheet.TILE_DIRT);
	}
}
