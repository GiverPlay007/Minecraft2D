package me.giverplay.minecraft2D.world.tiles;

import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.world.Tile;

public class SandTile extends Tile
{
	public SandTile(int x, int y, Boolean isFinal)
	{
		super(Material.SAND, x, y, true, isFinal, Spritesheet.TILE_SAND);
	}
	
	public SandTile(Integer x, Integer y)
	{
		super(Material.SAND, x, y, true, false, Spritesheet.TILE_SAND);
	}
}
