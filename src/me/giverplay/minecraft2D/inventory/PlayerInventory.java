package me.giverplay.minecraft2D.inventory;

import static me.giverplay.minecraft2D.world.World.TILE_SIZE;
import static me.giverplay.minecraft2D.world.World.canMove;

import java.awt.event.MouseEvent;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.inventory.items.AirItem;
import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.tiles.AirTile;

public class PlayerInventory implements Inventory
{
	private Item[] items;
	
	private Game game = Game.getGame();
	private Player player;
	
	private int size;
	private int focusedSlot = 0;
	
	public PlayerInventory(int size, Player holder)
	{
		this.size = size;
		this.items = new Item[size];
		this.player = holder;
		
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
	public boolean hasItem(Material type)
	{
		for(int i = 0; i < size; i++)
		{
			if(getItem(i).getType() == type)
				return true;
		}
		
		return false;
	}

	@Override
	public boolean addItem(Item item)
	{
		int rest = item.getAmount(), buffer = 0;
		boolean next = false;
		
		for(int i = 0; i < items.length; i++)
		{
			Item now = items[i];
			
			if(now.getType() == Material.AIR)
			{
				items[i] = !next ? item : item.setAmount(rest);
				return true;
			}
			
			if(now.getType() != item.getType() || now.getAmount() >= now.getMaxStack())
				continue;
			
			if(now.getAmount() + rest <= now.getMaxStack())
			{
				now.setAmount(now.getAmount() + rest);
				return true;
			}
			
			buffer = now.getMaxStack() - now.getAmount();
			rest -= buffer;
			now.setAmount(now.getMaxStack());
			next = true;
		}
		
		if(rest != 0)
			System.out.println("O inventário estava parcialmente cheio, " + rest + " itens se perderam...");
		
		return next;
	}

	@Override
	public void removeItem(Material type, int amount)
	{
		if(amount <= 0)
			throw new IllegalArgumentException("O número deve ser maior que um");
		
		int buffer = amount;
		
		for(int i = 0; i < size; i++)
		{			
			if(buffer <= 0)
				break;
			
			Item item = getItem(i);
			
			if(item.getType() != type)
				continue;
			
			if(item.getAmount() - buffer <= 0)
			{
				buffer -= item.getAmount();
				removeItem(i);
			}
			else
			{
				item.setAmount(item.getAmount() - buffer);
				break;
			}
		}
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
	
	public void handleClick(int x, int y, int button)
	{
		int xx = (x + game.getCamera().getX()) / TILE_SIZE;
		int yy = (y + game.getCamera().getY()) / TILE_SIZE;
		
		if(button == MouseEvent.BUTTON1)
		{
			removeTile(xx, yy);
		}
		else if(button == MouseEvent.BUTTON3)
		{
			placeTile(xx, yy);
		}
	}
	
	private void placeTile(int x, int y)
	{
		Tile[] tiles = game.getWorld().getTiles();
		int index = x + y * game.getWorld().getWidth();
		
		Tile tile = tiles[index];
		Material mat = tile.getType();
		
		if(tile.isFinal())
			return;
		
		Item item = getItem(getFocusedSlot());
		
		if(item.getType() == Material.AIR)
			return;
		
		tiles[index] = Tile.forMaterial(item.getType(), x * TILE_SIZE, y * TILE_SIZE);
		
		if(!canMove(player.getX(), player.getY()))
		{
			tiles[index] = Tile.forMaterial(mat, x * TILE_SIZE, y * TILE_SIZE);
		}
		
		removeItem(item.getType(), 1);
	}
	
	private void removeTile(int x, int y)
	{
		Tile[] tiles = game.getWorld().getTiles();
		int index = x + y * game.getWorld().getWidth();
		
		if(tiles[index].isFinal())
			return;
		
		Material mat = tiles[index].getType();
		addItem(Item.forMaterial(mat, 1));
		
		tiles[index] = new AirTile(x, y);
	}
}