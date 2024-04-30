package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.game.GameMode;
import me.giverplay.minecraft2D.world.Material;
import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.World;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Random;

public class PlayerInventory implements Inventory {

  private final Item[] items;
  private final int size;

  private final Game game;
  private final PlayerEntity player;

  private int focusedSlot = 0;

  public PlayerInventory(PlayerEntity holder, int size) {
    this.size = size;
    this.items = new Item[size];
    this.player = holder;
    this.game = player.getGame();

    clear();
  }

  @Override
  public Item[] getItems() {
    return items;
  }

  @Override
  public Item getItem(int slot) {
    if (slot < 0 || slot >= items.length)
      throw new ArrayIndexOutOfBoundsException("Slot inválido");

    return items[slot];
  }

  @Override
  public int firstEmpty() {
    for (int i = 0; i < items.length; i++)
      if (items[i] == null)
        return i;

    return -1;
  }

  @Override
  public boolean hasItem(Material type) {
    for (int i = 0; i < size; i++) {
      if (getItem(i).getType() == type)
        return true;
    }

    return false;
  }

  @Override
  public void removeItem(int slot, int amount) {
    Item item = getItem(slot);

    if (item.getType() == Material.AIR)
      return;

    if (item.getAmount() - amount <= 0) {
      removeItem(slot);
      return;
    }

    item.setAmount(item.getAmount() - amount);
  }

  @Override
  public boolean addItem(Item item) {
    int rest = item.getAmount(), buffer;
    boolean next = false;

    for (int i = 0; i < items.length; i++) {
      Item now = items[i];

      if (now.getType() == Material.AIR) {
        items[i] = !next ? item : item.setAmount(rest);
        return true;
      }

      if (now.getType() != item.getType() || now.getAmount() >= now.getMaxStack())
        continue;

      if (now.getAmount() + rest <= now.getMaxStack()) {
        now.setAmount(now.getAmount() + rest);
        return true;
      }

      buffer = now.getMaxStack() - now.getAmount();
      rest -= buffer;
      now.setAmount(now.getMaxStack());
      next = true;
    }

    if (rest != 0)
      System.out.println("O inventário estava parcialmente cheio, " + rest + " itens se perderam...");

    return next;
  }

  @Override
  public void removeItem(Material type, int amount) {
    if (amount <= 0)
      throw new IllegalArgumentException("O número deve ser maior que um");

    int buffer = amount;

    for (int i = 0; i < size; i++) {
      if (buffer <= 0)
        break;

      Item item = getItem(i);

      if (item.getType() != type)
        continue;

      if (item.getAmount() - buffer <= 0) {
        buffer -= item.getAmount();
        removeItem(i);
      } else {
        item.setAmount(item.getAmount() - buffer);
        break;
      }
    }
  }

  @Override
  public void removeItem(int slot) {
    if (slot < 0 || slot >= items.length)
      throw new ArrayIndexOutOfBoundsException("Slot inválido");

    items[slot] = new Item(Material.AIR, 1);
  }

  @Override
  public void setItem(int slot, Item item) {
    if (slot < 0 || slot >= items.length)
      throw new ArrayIndexOutOfBoundsException("Slot inválido");

    items[slot] = item;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public int getFocusedSlot() {
    return focusedSlot;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      items[i] = new Item(Material.AIR, 1);
    }
  }

  @Override
  public void removeItem(Material type) {
    for (int i = 0; i < size; i++) {
      if (getItem(0).getType() == type) {
        removeItem(i);
      }
    }
  }

  public void updateFocus(int toUpdate) {
    focusedSlot = toUpdate + focusedSlot < 0 ? 8 : toUpdate + focusedSlot > 8 ? 0 : focusedSlot + toUpdate;
  }

  public void setFocus(int set) {
    if (focusedSlot < 0 || focusedSlot > 8)
      throw new IllegalArgumentException("Slot deve ser entre 0 e 8");

    focusedSlot = set;
  }

  public void handleClick(int x, int y, int button) {
    int xx = (x + game.getCamera().getX()) / Tile.SIZE;
    int yy = (y + game.getCamera().getY()) / Tile.SIZE;

    if (button == MouseEvent.BUTTON1) {
      removeTile(xx, yy);
      return;
    }

    int creative = 0;
    int w = game.getWorld().getWidth();
    int h = game.getWorld().getHeight();

    int[] tiles = game.getWorld().getTiles();

    if (xx > 0)
      if (tiles[(xx - 1) + yy * w] == Material.AIR.getId())
        creative++;

    if (xx < w)
      if (tiles[(xx + 1) + yy * w] == Material.AIR.getId())
        creative++;


    if (yy > 0)
      if (tiles[xx + (yy - 1) * w] == Material.AIR.getId())
        creative++;

    if (yy < h)
      if (tiles[xx + (yy + 1) * w] == Material.AIR.getId())
        creative++;

    if (creative >= 4 && player.getGameMode() != GameMode.CREATIVE)
      return;

    if (button == MouseEvent.BUTTON3) {
      placeTile(xx, yy);
    }
  }

  private void placeTile(int x, int y) {
    World world = game.getWorld();

    int[] tiles = world.getTiles();
    int index = x + y * world.getWidth();

    if (world.isPermanentTile(index)) return;

    Item item = getItem(getFocusedSlot());

    if (item.getType() == Material.AIR)
      return;

    if (tiles[index] != Material.AIR.getId()) return;

    Rectangle playerRect = new Rectangle(player.getX() + player.getMaskX(), player.getY() + player.getMaskY(), player.getMaskWidth(), player.getMaskHeight());
    Rectangle rect = new Rectangle(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);

    if (playerRect.intersects(rect)) return;

    tiles[index] = item.getType().getId();
    world.makeChangedTile(index);

    if(player.getGameMode() != GameMode.CREATIVE) {
      removeItem(getFocusedSlot(), 1);
    }
  }

  private void removeTile(int x, int y) {
    World world = game.getWorld();
    int[] tiles = game.getWorld().getTiles();
    int index = x + y * game.getWorld().getWidth();

    if (world.isPermanentTile(index)) return;

    Material material = Material.getById(tiles[index]);

    if(player.getGameMode() != GameMode.CREATIVE) {
      addItem(new Item(material, 1));
    }

    tiles[index] = Material.AIR.getId();
    world.makeChangedTile(index);

    if(y -1 > 0) {
      Tile above = world.getTile(x, y-1);

      if(above.getMaterial() == Material.GRASS_BUSH) {
        int aboveIndex = x + (y -1) * world.getWidth();
        tiles[aboveIndex] = Material.AIR.getId();
        world.makeChangedTile(aboveIndex);

        Random random = new Random();

        if(random.nextInt(100) < 25) {
          addItem(new Item(Material.GRASS_BUSH, 1));
        }
      }
    }
  }

  public void resetDefaults() {
    setItem(0, new Item(Material.STONE, 64));
    setItem(1, new Item(Material.BEDROCK, 64));
    setItem(2, new Item(Material.STONE_BRICKS, 64));
    setItem(3, new Item(Material.BRICKS, 64));
    setItem(4, new Item(Material.GRASS, 64));
    setItem(5, new Item(Material.DIRT, 64));
    setItem(6, new Item(Material.SAND, 64));
    setItem(7, new Item(Material.WOOD, 64));
    setItem(8, new Item(Material.GRASS_BUSH, 64));
  }
}
