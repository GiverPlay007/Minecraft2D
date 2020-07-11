package me.giverplay.minecraft2D.inventory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

public class Item
{
	private String name;
	private BufferedImage sprite;
	private Material type;
	
	private int amount;
	private int maxStack;
	
	public Item(String name, Material type, int amount, int maxStack, BufferedImage sprite)
	{
		this.name = name;
		this.type = type;
		
		if(maxStack < 1 || maxStack > 64)
		  throw new IllegalArgumentException("Stack máximo deve ser entre 1 e 64");
		
		this.maxStack = maxStack;
		
		if(amount > maxStack || amount < 1)
			throw new IllegalArgumentException("A quantidade deve ser entre 1 e o stackSize");
		
		this.amount = amount;
		this.sprite = sprite;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Material getType()
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
	
	public Item setAmount(int amount)
	{
		this.amount = amount;
		return this;
	}

	public static Item forName(String name, int amount)
	{
		try
		{
			Class<?> clazz = Material.valueOf(name.toUpperCase()).getItemClass();
			Constructor<?> cons = clazz.getConstructor(Integer.class);
			Object obj = cons.newInstance(amount);
			
			return (Item) obj;
		}
		catch(Exception e)
		{
			System.out.println(e.getCause().getMessage());
			return null;
		}
	}
	
	public static Item forMaterial(Material mat, int amount)
	{
		try
		{
			Class<?> clazz = mat.getItemClass();
			Constructor<?> cons = clazz.getConstructor(Integer.class);
			Object obj = cons.newInstance(amount);
			
			return (Item) obj;
		}
		catch(Exception e)
		{
			System.out.println(e.getCause().getMessage());
			return null;
		}
	}
}
