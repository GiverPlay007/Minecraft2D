package me.giverplay.minecraft2D.game;

import static me.giverplay.minecraft2D.world.World.TILE_SIZE;

import java.util.List;

import org.json.JSONObject;

import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.Material;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.World;

public class GameData
{
	private String name;
	private Player player;
	private World world;
	private List<Entity> entities;
	
	private boolean load;
	
	public GameData()
	{
		load = true;
	}
	
	public GameData(String save, Player player, World world, List<Entity> entities)
	{
		load = false;
		
		this.name = save;
		this.player = player;
		this.world = world;
		this.entities = entities;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public List<Entity> getEtities()
	{
		return this.entities;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public String serialize()
	{
		JSONObject save = new JSONObject();
		JSONObject user = new JSONObject();
		JSONObject inventory = new JSONObject();
		JSONObject worldJ = new JSONObject();
		JSONObject tiles = new JSONObject();
		JSONObject items = new JSONObject();
		
		user.put("life", player.getLife());
		user.put("max_life", player.getMaxLife());
		user.put("x", player.getX());
		user.put("y", player.getY());
		
		Inventory inv = player.getInventory();
		inventory.put("size", inv.size());
		
		for(int i = 0; i < inv.size(); i++)
		{
			Item item = inv.getItem(i);
			
			if(item.getType() == Material.AIR)
				continue;
			
			JSONObject itemJ = new JSONObject();
			
			itemJ.put("id", item.getType().name());
			itemJ.put("amount", item.getAmount());
			itemJ.put("name", item.getName());
			items.put(String.valueOf(i), itemJ);
		}
		
		inventory.put("items", items);
		user.put("inventory", inventory);
		save.put("user", user);
		worldJ.put("width", world.getWidth());
		worldJ.put("height", world.getHeight());
		
		for(int i = 0; i < world.getTiles().length; i++)
		{
			Tile tile = world.getTiles()[i];
			
			if(tile.getType() == Material.AIR)
				continue;
			
			JSONObject tileJ = new JSONObject();
			
			tileJ.put("x", tile.getX());
			tileJ.put("y", tile.getY());
			tileJ.put("final", tile.isFinal());
			tileJ.put("rigid", tile.isRigid());
			tiles.put(String.valueOf(i), tileJ);
		}
		
		worldJ.put("tiles", tiles);
		save.put("world", worldJ);
		
		return save.toString();
	}
	
	public void decode(String data)
	{
		JSONObject json = new JSONObject(data);
		JSONObject user = json.getJSONObject("user");
		JSONObject world = json.getJSONObject("world");
		JSONObject inventory = user.getJSONObject("inventory");
		JSONObject tiles = world.getJSONObject("tiles");
		JSONObject items = inventory.getJSONObject("items");
		
		this.player = new Player(user.getInt("x"), user.getInt("y"), TILE_SIZE, TILE_SIZE);
		PlayerInventory inv = new PlayerInventory(inventory.getInt("size"), player);
		
		for(String key : items.keySet())
		{
			JSONObject obj = items.getJSONObject(key);
			Item item = Item.forMaterial(Material.valueOf(obj.getString("id")), obj.getInt("amount"));
			
			inv.setItem(Integer.parseInt(key), item);
		}
		
		this.world = new World(world.getInt("width"), world.getInt("height"));
	}
}
