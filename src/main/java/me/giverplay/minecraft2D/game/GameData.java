package me.giverplay.minecraft2D.game;

import me.giverplay.minecraft2D.entity.Entity;
import me.giverplay.minecraft2D.entity.EntityLiving;
import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.world.Material;
import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameData
{
	private String name;
	private PlayerEntity player;
	private World world;
	private ArrayList<Entity> entities;
	
	public GameData()
	{

	}
	
	public GameData(String save, PlayerEntity player, World world, ArrayList<Entity> entities)
	{
		this.name = save;
		this.player = player;
		this.world = world;
		this.entities = entities;
	}

	public String serialize()
	{
		JSONObject save = new JSONObject();
		JSONObject playerJson = new JSONObject();
		JSONObject worldJson = new JSONObject();
		JSONArray entitiesJson = new JSONArray();

		savePlayer(playerJson);
		saveWorld(worldJson);
		saveEntities(entitiesJson);

		save.put("player", playerJson);
		save.put("world", worldJson);
		save.put("entities", entitiesJson);

		return save.toString();
	}

	private void savePlayer(JSONObject playerJson)
	{
		playerJson.put("x", player.getX());
		playerJson.put("y", player.getY());
		playerJson.put("life", player.getLife());
		playerJson.put("maxLife", player.getMaxLife());
		playerJson.put("gameMode", player.getGameMode().name());

		JSONObject inventoryJson = new JSONObject();
		saveInventory(inventoryJson, (PlayerInventory) player.getInventory());

		playerJson.put("inventory", inventoryJson);
	}

	private void saveInventory(JSONObject inventoryJson, PlayerInventory inventory)
	{
		JSONArray items = new JSONArray();

		for(int slot = 0; slot < inventory.size(); slot++)
		{
			Item item = inventory.getItem(slot);

			if(item.getType() == Material.AIR)
				continue;

			JSONObject itemJson = new JSONObject();
			itemJson.put("slot", slot);
			itemJson.put("type", item.getType().name());
			itemJson.put("amount", item.getAmount());

			itemJson.put(Integer.toString(slot), itemJson);
		}

		inventoryJson.put("size", inventory.size());
		inventoryJson.put("items", items);
	}

	private void saveWorld(JSONObject worldJson)
	{
		worldJson.put("width", world.getWidth());
		worldJson.put("height", world.getHeight());
		worldJson.put("seed", world.getSeed());
		worldJson.put("gameTime", world.getGameTime());

		saveTiles(worldJson);
	}

	private void saveTiles(JSONObject worldJson)
	{
		JSONObject tilesJson = new JSONObject();

		Tile[] tiles = world.getTiles();

		for(int index = 0; index < tiles.length; index++)
		{
			Tile tile = tiles[index];

			if(!tile.modified())
				continue;

			tilesJson.put(Integer.toString(index), tile.getType().name());
		}

		worldJson.put("tiles", tilesJson);
	}

	private void saveEntities(JSONArray entitiesJson)
	{
		ArrayList<Entity> entities = this.entities;

		for(Entity entity : entities)
		{
			if(entity == player)
				continue;

			JSONObject entityJson = new JSONObject();
			entityJson.put("class", entity.getClass().getName());
			entityJson.put("x", entity.getX());
			entityJson.put("y", entity.getY());

			if(entity instanceof EntityLiving)
			{
				entityJson.put("life", ((EntityLiving) entity).getLife());
				entityJson.put("life", ((EntityLiving) entity).getMaxLife());
			}

			entitiesJson.put(entityJson);
		}
	}

	public void decodeAndApply(Game game, String data)
	{

	}

	private void apply(Game game)
	{
		game.world = this.world;
		game.player = this.player;
		game.entities.clear();
		game.entities.addAll(this.entities);
	}

	public PlayerEntity getPlayer()
	{
		return this.player;
	}

	public ArrayList<Entity> getEntities()
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
