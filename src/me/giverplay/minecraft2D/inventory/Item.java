package me.giverplay.minecraft2D.inventory;

import java.awt.image.BufferedImage;

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
}
