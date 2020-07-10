package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.inventory.items.AirItem;
import me.giverplay.minecraft2D.inventory.items.DirtItem;
import me.giverplay.minecraft2D.inventory.items.GrassItem;
import me.giverplay.minecraft2D.inventory.items.SandItem;
import me.giverplay.minecraft2D.inventory.items.StoneItem;
import me.giverplay.minecraft2D.world.DirtTile;
import me.giverplay.minecraft2D.world.tiles.AirTile;
import me.giverplay.minecraft2D.world.tiles.GrassTile;
import me.giverplay.minecraft2D.world.tiles.SandTile;
import me.giverplay.minecraft2D.world.tiles.StoneTile;

public enum Material
{
	STONE(StoneItem.class, StoneTile.class), 
	GRASS(GrassItem.class, GrassTile.class), 
	DIRT(DirtItem.class, DirtTile.class), 
	SAND(SandItem.class, SandTile.class),
	AIR(AirItem.class, AirTile.class);
	
	private Class<?> clazz;
	private Class<?> tileClazz;
	
	Material(Class<?> clazz, Class<?> tileClazz)
	{
		this.clazz = clazz;
		this.tileClazz = tileClazz;
	}
	
	public Class<?> getItemClass()
	{
		return this.clazz;
	}
	
	public Class<?> getTileClass()
	{
		return this.tileClazz;
	}
}
