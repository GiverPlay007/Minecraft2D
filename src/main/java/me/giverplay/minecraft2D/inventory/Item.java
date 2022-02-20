package me.giverplay.minecraft2D.inventory;

import me.giverplay.minecraft2D.world.Material;

import java.awt.image.BufferedImage;

public class Item {

  private final BufferedImage sprite;
  private final Material type;
  private final int maxStack;

  private String name;
  private int amount;

  public Item(Material type, int amount) {
    this.type = type;
    this.name = type.getName();

    this.maxStack = type.maxStackSize();

    if(maxStack < 1 || maxStack > 64)
      throw new IllegalArgumentException("Stack máximo deve ser entre 1 e 64");

    if(amount > maxStack || amount < 1)
      throw new IllegalArgumentException("A quantidade deve ser entre 1 e o stackSize");

    this.amount = amount;
    this.sprite = type.getSprite();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String customName) {
    this.name = customName;
  }

  public Material getType() {
    return this.type;
  }

  public BufferedImage getSprite() {
    return this.sprite;
  }

  public int getAmount() {
    return this.amount;
  }

  public int getMaxStack() {
    return this.maxStack;
  }

  public Item setAmount(int amount) {
    if(amount > maxStack || amount < 1) {
      throw new IllegalArgumentException("Quantidade inválida.");
    } else {
      this.amount = amount;
    }

    return this;
  }
}
