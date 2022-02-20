package me.giverplay.minecraft2D.world;

import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.graphics.Sprites;

public enum Material {
  STONE("Stone", 64, true, true, false, Sprites.TILE_STONE),
  GRASS("Grass", 64, true, true, false, Sprites.TILE_GRASS),
  DIRT("Dirt", 64, true, true, false, Sprites.TILE_DIRT),
  SAND("Sand", 64, true, true, false, Sprites.TILE_SAND),
  AIR("Air", 64, true, false, false, null),
  BEDROCK("Bedrock", 64, true, true, true, Sprites.TILE_BEDROCK),
  STONE_BRICKS("Stone Bricks", 64, true, true, false, Sprites.TILE_STONE_BRICKS),
  WOOD("Wood", 64, true, true, false, Sprites.TILE_WOOD),
  BRICKS("Bricks", 64, true, true, false, Sprites.TILE_BRICKS);

  private final String name;
  private final BufferedImage sprite;

  private final int maxStack;

  private final boolean isStackable;
  private final boolean isRigid;
  private final boolean creativeOnly;

  Material(String name, int itemMaxSize, boolean itemStackable, boolean tileRigid, boolean creativeOnly, BufferedImage sprite) {
    this.name = name;
    this.maxStack = itemMaxSize;
    this.isStackable = itemStackable;
    this.isRigid = tileRigid;
    this.creativeOnly = creativeOnly;
    this.sprite = sprite;
  }

  public String getName() {
    return this.name;
  }

  public boolean isStackable() {
    return this.isStackable;
  }

  public boolean isRigid() {
    return this.isRigid;
  }

  public boolean isCreativeOnly() {
    return this.creativeOnly;
  }

  public BufferedImage getSprite() {
    return this.sprite;
  }

  public int maxStackSize() {
    return this.maxStack;
  }

  public static Material parse(String name) {
    try {
      return valueOf(name.toUpperCase());
    } catch (IllegalArgumentException e) {
      return Material.AIR;
    }
  }

  public static boolean isMaterial(String name) // TODO: Get by name and description name
  {
    try {
      valueOf(name.toUpperCase());
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}