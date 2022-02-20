package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.world.Material;

public interface Inventory {
  Item[] getItems();

  Item getItem(int slot);

  int firstEmpty();

  int size();

  int getFocusedSlot();

  boolean hasItem(Material type);

  boolean addItem(Item item);

  void removeItem(Material type, int amount);

  void removeItem(Material type);

  void removeItem(int slot);

  void removeItem(int slot, int amount);

  void setItem(int slot, Item item);

  void clear();
}
