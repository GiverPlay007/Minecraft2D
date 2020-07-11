package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.inventory.items.AirItem;
import me.giverplay.minecraft2D.inventory.items.BedrockItem;
import me.giverplay.minecraft2D.inventory.items.BricksItem;
import me.giverplay.minecraft2D.inventory.items.DirtItem;
import me.giverplay.minecraft2D.inventory.items.GrassItem;
import me.giverplay.minecraft2D.inventory.items.SandItem;
import me.giverplay.minecraft2D.inventory.items.StoneBricksItem;
import me.giverplay.minecraft2D.inventory.items.StoneItem;
import me.giverplay.minecraft2D.inventory.items.WoodItem;
import me.giverplay.minecraft2D.world.tiles.AirTile;
import me.giverplay.minecraft2D.world.tiles.BedrockTile;
import me.giverplay.minecraft2D.world.tiles.BricksTile;
import me.giverplay.minecraft2D.world.tiles.DirtTile;
import me.giverplay.minecraft2D.world.tiles.GrassTile;
import me.giverplay.minecraft2D.world.tiles.SandTile;
import me.giverplay.minecraft2D.world.tiles.StoneBricksTile;
import me.giverplay.minecraft2D.world.tiles.StoneTile;
import me.giverplay.minecraft2D.world.tiles.WoodTile;

public enum Material
{
	STONE(StoneItem.class, StoneTile.class), 
	GRASS(GrassItem.class, GrassTile.class), 
	DIRT(DirtItem.class, DirtTile.class), 
	SAND(SandItem.class, SandTile.class),
	AIR(AirItem.class, AirTile.class),
	BEDROCK(BedrockItem.class, BedrockTile.class),
	STONE_BRICKS(StoneBricksItem.class, StoneBricksTile.class),
	WOOD(WoodItem.class, WoodTile.class),
	BRICKS(BricksItem.class, BricksTile.class);
	
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
