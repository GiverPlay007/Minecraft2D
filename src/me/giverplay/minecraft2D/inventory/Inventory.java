package me.giverplay.minecraft2D.inventory;

public interface Inventory
{
	public Item[] getItems();
	public Item getItem(int slot);
	
	public int firstEmpty();
	public int items();
	
	public boolean hasItem(ItemEnum type);
	public boolean addItem(Item item);
	
	public void removeItem(ItemEnum type, int amount);
	public void removeItem(int slot);
	public void setItem(int slot, Item item);
}
