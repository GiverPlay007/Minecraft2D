package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class GrassTile extends Tile
{
	public GrassTile(int x, int y, Boolean isFinal)
	{
		super(Material.GRASS, x, y, true, isFinal, Spritesheet.TILE_GRASS);
	}
	
	public GrassTile(Integer x, Integer y)
	{
		super(Material.GRASS, x, y, true, false, Spritesheet.TILE_GRASS);
	}
}
