package me.giverplay.minecraft2D.game;

import java.util.ArrayList;
import java.util.List;

import me.giverplay.minecraft2D.Game;
import org.json.JSONObject;

import me.giverplay.minecraft2D.entity.entities.EnemyEntity;
import me.giverplay.minecraft2D.entity.Entity;
import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.world.Material;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.World;

public class GameData
{
	private String name;
	private PlayerEntity player;
	private World world;
	private List<Entity> entities;
	
	public GameData()
	{
		
	}
	
	public GameData(String save, PlayerEntity player, World world, List<Entity> entities)
	{
		this.name = save;
		this.player = player;
		this.world = world;
		this.entities = entities;
	}

	public String serialize()
	{
		JSONObject save = new JSONObject();
		return save.toString();
	}

	public String serializez()
	{
		JSONObject save = new JSONObject();
		JSONObject user = new JSONObject();
		JSONObject inventory = new JSONObject();
		JSONObject worldJ = new JSONObject();
		JSONObject tiles = new JSONObject();
		JSONObject items = new JSONObject();
		JSONObject entity = new JSONObject();
		JSONObject enemy = new JSONObject();
		
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
			items.put(String.valueOf(i), itemJ);
		}
		
		inventory.put("items", items);
		user.put("inventory", inventory);
		save.put("user", user);
		worldJ.put("width", world.getWidth());
		worldJ.put("height", world.getHeight());
		worldJ.put("seed", world.getSeed());
		
		for(int i = 0; i < world.getTiles().length; i++)
		{
			Tile tile = world.getTiles()[i];
			
			if(!tile.modified())
				continue;
			
			JSONObject tileJ = new JSONObject();
			
			tileJ.put("id", tile.getType().name());
			tiles.put(String.valueOf(i), tileJ);
		}
		
		for(int i = 0; i < entities.size(); i++)
		{
			Entity ent = entities.get(i);
			
			if(ent instanceof PlayerEntity)
				continue;
			
			if(ent instanceof EnemyEntity)
			{
				EnemyEntity lent = (EnemyEntity) ent;
				JSONObject entJ = new JSONObject();
				
				entJ.put("x", lent.getX());
				entJ.put("y", lent.getY());
				entJ.put("life", lent.getMaxLife());
				entJ.put("max_life", lent.getMaxLife());
				enemy.put("enemies", "entJ");
			}
		}
		
		entity.put("enemies", enemy);
		save.put("entities", entity);
		worldJ.put("tiles", tiles);
		save.put("world", worldJ);
		
		return save.toString();
	}
	
	public void decode(Game game, String data)
	{
		JSONObject json = new JSONObject(data);
		JSONObject user = json.getJSONObject("user");
		JSONObject world = json.getJSONObject("world");
		JSONObject inventory = user.getJSONObject("inventory");
		JSONObject tiles = world.getJSONObject("tiles");
		JSONObject items = inventory.getJSONObject("items");
		JSONObject entity = json.getJSONObject("entities");
		JSONObject enemy = entity.getJSONObject("enemies");
		
		Tile[] til = new Tile[world.getInt("width") * world.getInt("height")];
		
		for(String key : tiles.keySet())
		{
			JSONObject obj = tiles.getJSONObject(key);
			
			til[Integer.parseInt(key)] = new Tile(Material.valueOf(obj.getString("id")));
		}
		
		this.world = new World(game, world.getInt("width"), world.getInt("height"), til, world.getDouble("seed"));
		this.entities = new ArrayList<>();
		this.player = new PlayerEntity(game, user.getInt("x"), user.getInt("y"));
		this.player.setLife(user.getInt("life"));
		this.player.setMaxLife(user.getInt("max_life"));
		this.entities.add(player);

		PlayerInventory inv = new PlayerInventory(inventory.getInt("size"), player);
		this.player.setInventory(inv);
		
		for(String key : enemy.keySet())
		{
			JSONObject ene = enemy.getJSONObject(key);
			EnemyEntity en = new EnemyEntity(game, ene.getInt("x"), ene.getInt("y"));
			entities.add(en);
		}
		
		for(String key : items.keySet())
		{
			JSONObject obj = items.getJSONObject(key);
			Item item = new Item(Material.valueOf(obj.getString("id")), obj.getInt("amount"));
			inv.setItem(Integer.parseInt(key), item);
		}

		
	}

	public PlayerEntity getPlayer()
	{
		return this.player;
	}

	public List<Entity> getEntities()
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

	public void setName(String worldName)
	{
		this.name = worldName;
	}
}
