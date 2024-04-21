package me.giverplay.minecraft2D.world;

import me.giverplay.minecraft2D.world.tiles.AirTile;
import me.giverplay.minecraft2D.world.tiles.BedrockTile;
import me.giverplay.minecraft2D.world.tiles.BricksTile;
import me.giverplay.minecraft2D.world.tiles.DirtTile;
import me.giverplay.minecraft2D.world.tiles.GrassBushTile;
import me.giverplay.minecraft2D.world.tiles.GrassTile;
import me.giverplay.minecraft2D.world.tiles.SandTile;
import me.giverplay.minecraft2D.world.tiles.StoneBricksTile;
import me.giverplay.minecraft2D.world.tiles.StoneTile;
import me.giverplay.minecraft2D.world.tiles.WoodTile;

public class Tiles {
  private Tiles() {
  }

  private static final Tile[] tiles = new Tile[Material.values().length];

  public static final AirTile AIR = (AirTile) registerTile(new AirTile());
  public static final StoneTile STONE = (StoneTile) registerTile(new StoneTile());
  public static final GrassTile GRASS = (GrassTile) registerTile(new GrassTile());
  public static final DirtTile DIRT = (DirtTile) registerTile(new DirtTile());
  public static final SandTile SAND = (SandTile) registerTile(new SandTile());
  public static final BedrockTile BEDROCK = (BedrockTile) registerTile(new BedrockTile());
  public static final StoneBricksTile STONE_BRICKS = (StoneBricksTile) registerTile(new StoneBricksTile());
  public static final WoodTile WOOD = (WoodTile) registerTile(new WoodTile());
  public static final BricksTile BRICKS = (BricksTile) registerTile(new BricksTile());
  public static final GrassBushTile GRASS_BUSH = (GrassBushTile) registerTile(new GrassBushTile());


  private static Tile registerTile(Tile tile) {
    return tiles[tile.getMaterial().id] = tile;
  }

  public static Tile getById(int id) {
    if (id >= tiles.length || id < 0) throw new IllegalArgumentException("Unknown tile ID");

    return tiles[id];
  }

  public static Tile getByMaterial(Material material) {
    for (Tile tile : tiles) {
      if (tile.getMaterial() == material) return tile;
    }

    return null;
  }
}
