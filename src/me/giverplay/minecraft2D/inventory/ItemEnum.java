package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.inventory.items.GrassItem;

public enum ItemEnum
{
	STONE(GrassItem.class), 
	GRASS(GrassItem.class), 
	DIRT(GrassItem.class), 
	SAND(GrassItem.class);
	
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
