package me.giverplay.minecraft2D.inventory;

import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.graphics.Spritesheet;

public enum Material
{
	STONE("Stone", 64, true, true, false, Spritesheet.TILE_STONE), 
	GRASS("Grass", 64, true, true, false, Spritesheet.TILE_GRASS), 
	DIRT("Dirt", 64, true, true, false, Spritesheet.TILE_DIRT), 
	SAND("Sand", 64, true, true, false, Spritesheet.TILE_SAND),
	AIR("Air", 64, true, false, false, null),
	BEDROCK("Bedrock", 64, true, true, true, Spritesheet.TILE_BEDROCK),
	STONE_BRICKS("Stone Bricks", 64, true, true, false, Spritesheet.TILE_STONE_BRICKS),
	WOOD("Wood", 64, true, true, false, Spritesheet.TILE_WOOD),
	BRICKS("Bricks", 64, true, true, false, Spritesheet.TILE_BRICKS);
	
	private String name;
	private BufferedImage sprite;
	
	private int maxStack;
	
	private boolean isStackable;
	private boolean isRigid;
	private boolean creativeOnly;
	
	Material(String name, int itemMaxSize, boolean itemStackable, boolean tileRigid, boolean creativeOnly, BufferedImage sprite)
	{
		this.name = name;
		this.maxStack = itemMaxSize;
		this.isStackable = itemStackable;
		this.isRigid = tileRigid;
		this.creativeOnly = creativeOnly;
		this.sprite = sprite;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean isStackable()
	{
		return this.isStackable;
	}
	
	public boolean isRigid()
	{
		return this.isRigid;
	}
	
	public boolean isCreativeOnly()
	{
		return this.creativeOnly;
	}
	
	public BufferedImage getSprite() 
	{
		return this.sprite;
	}
	
	public int maxStackSize()
	{
		return this.maxStack;
	}
}