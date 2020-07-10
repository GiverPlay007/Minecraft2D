package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.inventory.items.DirtItem;
import me.giverplay.minecraft2D.inventory.items.GrassItem;
import me.giverplay.minecraft2D.inventory.items.SandItem;
import me.giverplay.minecraft2D.inventory.items.StoneItem;

public enum ItemEnum
{
	STONE(StoneItem.class), 
	GRASS(GrassItem.class), 
	DIRT(DirtItem.class), 
	SAND(SandItem.class);
	
	private Class<?> clazz;
	
	ItemEnum(Class<?> clazz)
	{
		this.clazz = clazz;
	}
	
	public Class<?> getItemClass()
	{
		return this.clazz;
	}
}
