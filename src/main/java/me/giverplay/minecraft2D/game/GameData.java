package me.giverplay.minecraft2D.game;

import me.giverplay.minecraft2D.entity.Entity;
import me.giverplay.minecraft2D.entity.EntityLiving;
import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.utils.ReflectionUtils;
import me.giverplay.minecraft2D.world.Material;
import me.giverplay.minecraft2D.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class GameData {

  private final Game game;

  private ArrayList<Entity> entities;
  private PlayerEntity player;
  private String name;
  private World world;

  public GameData(Game game) {
    this(game, null, null, null, null);
  }

  public GameData(Game game, String save, PlayerEntity player, World world, ArrayList<Entity> entities) {
    this.game = game;
    this.name = save;
    this.player = player;
    this.world = world;
    this.entities = entities;
  }

  public String serialize() {
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

  private void savePlayer(JSONObject playerJson) {
    playerJson.put("x", player.getX());
    playerJson.put("y", player.getY());
    playerJson.put("life", player.getLife());
    playerJson.put("maxLife", player.getMaxLife());
    playerJson.put("gameMode", player.getGameMode().name());

    JSONObject inventoryJson = new JSONObject();
    saveInventory(inventoryJson, (PlayerInventory) player.getInventory());

    playerJson.put("inventory", inventoryJson);
  }

  private void saveInventory(JSONObject inventoryJson, PlayerInventory inventory) {
    JSONArray items = new JSONArray();

    for (int slot = 0; slot < inventory.size(); slot++) {
      Item item = inventory.getItem(slot);

      if (item.getType() == Material.AIR)
        continue;

      JSONObject itemJson = new JSONObject();
      itemJson.put("slot", slot);
      itemJson.put("type", item.getType().name());
      itemJson.put("amount", item.getAmount());

      JSONObject slotItemJson = new JSONObject();
      slotItemJson.put("slot", slot);
      slotItemJson.put("item", itemJson);
      items.put(slotItemJson);
    }

    inventoryJson.put("size", inventory.size());
    inventoryJson.put("items", items);
  }

  private void saveWorld(JSONObject worldJson) {
    worldJson.put("width", world.getWidth());
    worldJson.put("height", world.getHeight());
    worldJson.put("seed", world.getSeed());
    worldJson.put("gameTime", world.getGameTime());

    saveTiles(worldJson);
  }

  private void saveTiles(JSONObject worldJson) {
    JSONObject tilesJson = new JSONObject();
    JSONObject tilesDataJson = new JSONObject();

    int[] tiles = world.getTiles();
    int[] tilesData = world.getTilesData();

    for (int index = 0; index < tiles.length; index++) {
      if (!world.isTileChanged(index)) continue;

      tilesJson.put(Integer.toString(index), tiles[index]);
      tilesDataJson.put(Integer.toString(index), tilesData[index]);
    }

    worldJson.put("tiles", tilesJson);
    worldJson.put("tilesData", tilesDataJson);
  }

  private void saveEntities(JSONArray entitiesJson) {
    ArrayList<Entity> entities = this.entities;

    for (Entity entity : entities) {
      if (entity == player)
        continue;

      JSONObject entityJson = new JSONObject();
      entityJson.put("class", entity.getClass().getSimpleName());
      entityJson.put("x", entity.getX());
      entityJson.put("y", entity.getY());

      if (entity instanceof EntityLiving) {
        entityJson.put("life", ((EntityLiving) entity).getLife());
        entityJson.put("life", ((EntityLiving) entity).getMaxLife());
      }

      entitiesJson.put(entityJson);
    }
  }

  public void deserializeAndApply(String data) {
    JSONObject save = new JSONObject(data);
    JSONObject playerJson = save.getJSONObject("player");
    JSONObject worldJson = save.getJSONObject("world");
    JSONArray entitiesJson = save.getJSONArray("entities");

    deserializePlayer(playerJson);
    deserializeWorld(worldJson);
    deserializeEntities(entitiesJson);

    apply();
  }

  private void deserializePlayer(JSONObject playerJson) {
    player = new PlayerEntity(game, playerJson.getInt("x"), playerJson.getInt("y"));

    player.setInventory(deserializePlayerInventory(playerJson.getJSONObject("inventory")));
    player.setGameMode(GameMode.parse(playerJson.getString("gameMode")));
    player.setMaxLife(playerJson.getInt("maxLife"));
    player.setLife(playerJson.getInt("life"));
  }

  private PlayerInventory deserializePlayerInventory(JSONObject inventoryJson) {
    PlayerInventory inventory = new PlayerInventory(player, inventoryJson.getInt("size"));

    JSONArray itemsArray = inventoryJson.getJSONArray("items");

    for (int index = 0; index < itemsArray.length(); index++) {
      JSONObject slotItem = itemsArray.getJSONObject(index);
      int slot = slotItem.getInt("slot");

      JSONObject item = slotItem.getJSONObject("item");
      Material type = Material.parse(item.getString("type"));

      int amount = item.getInt("amount");
      inventory.setItem(slot, new Item(type, amount));
    }

    return inventory;
  }

  private void deserializeWorld(JSONObject worldJson) {
    int width = worldJson.getInt("width");
    int height = worldJson.getInt("height");
    double seed = worldJson.getDouble("seed");
    long gameTime = worldJson.getLong("gameTime");

    JSONObject tilesJson = worldJson.getJSONObject("tiles");
    JSONObject tilesDataJson = worldJson.getJSONObject("tilesData");
    int[] tilesArray = deserializeTiles(tilesJson, width, height);
    int[] tilesDataArray = deserializeTilesData(tilesDataJson, width, height);

    world = new World(game, width, height, tilesArray, tilesDataArray, seed);
    world.setGameTime(gameTime);
  }

  private int[] deserializeTiles(JSONObject tilesJson, int width, int height) {
    int[] tilesArray = new int[width * height];
    Arrays.fill(tilesArray, -1);

    for (String key : tilesJson.keySet()) {
      tilesArray[Integer.parseInt(key)] = tilesJson.getInt(key);
    }

    return tilesArray;
  }

  private int[] deserializeTilesData(JSONObject tilesDataJson, int width, int height) {
    int[] tilesDataArray = new int[width * height];

    for (String key : tilesDataJson.keySet()) {
      tilesDataArray[Integer.parseInt(key)] = tilesDataJson.getInt(key);
    }

    return tilesDataArray;
  }

  private void deserializeEntities(JSONArray entitiesJson) {
    entities = new ArrayList<>();

    for (int index = 0; index < entitiesJson.length(); index++) {
      JSONObject entityJson = entitiesJson.getJSONObject(index);
      String clazz = entityJson.getString("class");
      Entity entity;

      int x = entityJson.getInt("x");
      int y = entityJson.getInt("y");

      try {
        entity = ReflectionUtils.newEntityInstance(clazz, game, x, y);
      } catch (Exception e) {
        System.out.println("Falha ao criar instância de " + clazz);
        e.printStackTrace();
        continue;
      }

      if (entity instanceof EntityLiving) {
        ((EntityLiving) entity).setLife(entityJson.getInt("life"));
        ((EntityLiving) entity).setMaxLife(entityJson.getInt("maxLife"));
      }

      entities.add(entity);
    }
  }

  private void apply() {
    entities.add(player);
    game.world = this.world;
    game.player = this.player;
    game.entities.clear();
    game.entities.addAll(this.entities);
  }

  public PlayerEntity getPlayer() {
    return this.player;
  }

  public ArrayList<Entity> getEntities() {
    return this.entities;
  }

  public String getName() {
    return this.name;
  }

  public World getWorld() {
    return this.world;
  }

  public void setName(String worldName) {
    this.name = worldName;
  }
}
