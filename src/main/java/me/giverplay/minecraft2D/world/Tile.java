package me.giverplay.minecraft2D.world;

import me.giverplay.minecraft2D.game.Camera;

import java.awt.Graphics;

public class Tile {
  public static final int SIZE = 16;

  private final Material material;

  public Tile(Material material) {
    this.material = material;
  }

  public void render(Graphics graphics, int x, int y, Camera camera) {
    if (material.getSprite() != null) {
      graphics.drawImage(material.getSprite(), x * SIZE - camera.getX(), y * SIZE - camera.getY(), SIZE, SIZE, null);
    }
  }

  public String getName() {
    return material.name;
  }

  public Material getMaterial() {
    return this.material;
  }

  public boolean isRigid() {
    return material.isRigid;
  }

  public boolean creativeOnly() {
    return material.creativeOnly;
  }
}
