package me.giverplay.minecraft2D.inventory;

public interface Inventory
{
	public Item[] getItems();
	public Item getItem(int slot);
	
	public int firstEmpty();
	public int size();
	public int getFocusedSlot();
	
	public boolean hasItem(Material type);
	public boolean addItem(Item item);
	
	public void removeItem(Material type, int amount);
	public void removeItem(int slot);
	public void setItem(int slot, Item item);
}
