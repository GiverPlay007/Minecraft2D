package me.giverplay.minecraft2D.world;

import me.giverplay.minecraft2D.graphics.Sprites;

import java.awt.image.BufferedImage;

public enum Material {
  AIR(0, "Air", 64, true, false, false, null),
  STONE(1, "Stone", 64, true, true, false, Sprites.TILE_STONE),
  GRASS(2, "Grass", 64, true, true, false, Sprites.TILE_GRASS),
  DIRT(3, "Dirt", 64, true, true, false, Sprites.TILE_DIRT),
  SAND(4, "Sand", 64, true, true, false, Sprites.TILE_SAND),
  BEDROCK(5, "Bedrock", 64, true, true, true, Sprites.TILE_BEDROCK),
  STONE_BRICKS(6, "Stone Bricks", 64, true, true, false, Sprites.TILE_STONE_BRICKS),
  WOOD(7, "Wood", 64, true, true, false, Sprites.TILE_WOOD),
  BRICKS(8, "Bricks", 64, true, true, false, Sprites.TILE_BRICKS),
  GRASS_BUSH(9, "Grass Bush", 64, true, false, false, Sprites.TILE_GRASS_BUSH);

  final String name;
  final BufferedImage sprite;

  final int id;
  final int maxStack;

  final boolean isStackable;
  final boolean isRigid;
  final boolean creativeOnly;

  Material(int id, String name, int itemMaxSize, boolean itemStackable, boolean tileRigid, boolean creativeOnly, BufferedImage sprite) {
    this.id = id;
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

  public int getId() {
    return id;
  }

  public static Material getById(int id) {
    for (Material material : values()) {
      if (material.id == id) return material;
    }

    return null;
  }

  public static boolean isMaterial(String name) {
    try {
      valueOf(name.toUpperCase());
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}