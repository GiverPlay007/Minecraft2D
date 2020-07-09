package me.giverplay.minecraft2D.inventory;

public class PlayerInventory implements Inventory
{
	private Item[] items;
	
	private int size;
	
	public PlayerInventory(int size)
	{
		this.size = size;
		
		this.items = new Item[size];
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
		return 0;
	}

	@Override
	public int items()
	{
		return 0;
	}

	@Override
	public boolean hasItem(ItemEnum type)
	{
		return false;
	}

	@Override
	public boolean addItem(Item item)
	{
		return false;
	}

	@Override
	public void removeItem(ItemEnum type, int amount)
	{
	}

	@Override
	public void removeItem(int slot)
	{
		if(slot < 0 || slot >= items.length)
			throw new ArrayIndexOutOfBoundsException("Slot inválido");
		
		items[slot] = null;
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
}
