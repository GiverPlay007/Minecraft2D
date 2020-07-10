package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.inventory.items.AirItem;

public class PlayerInventory implements Inventory
{
	private Item[] items;
	
	private int size;
	private int focusedSlot = 0;
	
	public PlayerInventory(int size)
	{
		this.size = size;
		this.items = new Item[size];
		
		for(int i = 0; i < size; i++)
		{
			items[i] = new AirItem(1);
		}
	}
	
	@Override
	public Item[] getItems()
	{
	  return items;
	}

	@Override
	public Item getItem(int slot)
	{
		if(slot < 0 || slot >= items.length)
			throw new ArrayIndexOutOfBoundsException("Slot inválido");
		
		return items[slot];
	}

	@Override
	public int firstEmpty()
	{
		for(int i = 0; i < items.length; i++)
			if(items[i] == null)
				return i;
		
		return -1;
	}

	@Override
	public int items()
	{
		return 0; // TODO
	}

	@Override
	public boolean hasItem(Material type)
	{
		return false;
	}

	@Override
	public boolean addItem(Item item)
	{
		for(int i = 0; i < items.length; i++)
		{
			if(items[i].getType() == Material.AIR)
			{
				items[i] = item;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void removeItem(Material type, int amount)
	{
		// TODO
	}

	@Override
	public void removeItem(int slot)
	{
		if(slot < 0 || slot >= items.length)
			throw new ArrayIndexOutOfBoundsException("Slot inválido");
		
		items[slot] = new AirItem(1);
	}

	@Override
	public void setItem(int slot, Item item)
	{
		if(slot < 0 || slot >= items.length)
			throw new ArrayIndexOutOfBoundsException("Slot inválido");
		
		items[slot] = item;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public int getFocusedSlot()
	{
		return focusedSlot;
	}
	
	public void updateFocus(int toUpdate)
	{
		focusedSlot = toUpdate + focusedSlot < 0 ? 8 : toUpdate + focusedSlot > 8 ? 0 : focusedSlot + toUpdate;
	}
}
