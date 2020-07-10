package me.giverplay.minecraft2D.inventory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

public class Item
{
	private String name;
	private BufferedImage sprite;
	private ItemEnum type;
	
	private int amount;
	private int maxStack;
	
	public Item(String name, ItemEnum type, int amount, int maxStack, BufferedImage sprite)
	{
		this.name = name;
		this.type = type;
		this.amount = amount;
		this.maxStack = maxStack;
		this.sprite = sprite;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ItemEnum getType()
	{
		return this.type;
	}
	
	public BufferedImage getSprite()
	{
		return this.sprite;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public int getMaxStack()
	{
		return this.maxStack;
	}
	
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public static Item fromName(String name, int amount)
	{
		try
		{
			Class<?> clazz = ItemEnum.valueOf(name.toUpperCase()).getItemClass();
			Constructor<?> cons = clazz.getConstructor(Integer.class);
			Object obj = cons.newInstance(amount);
			
			return (Item) obj;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
