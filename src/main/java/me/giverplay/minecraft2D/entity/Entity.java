package me.giverplay.minecraft2D.entity;

import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.world.World;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Supplier;

public abstract class Entity {

  protected static final Random RANDOM = new Random();

  protected final Game game;
  protected final Supplier<World> world;

  protected static Random random = new Random();

  protected double x;
  protected double y;

  protected int maskX = 0;
  protected int maskY = 0;
  protected int maskWidth = 0;
  protected int maskHeight = 0;

  protected final int width;
  protected final int height;
  protected int depth;

  public Entity(Game game, double x, double y, int width, int height) {
    this.game = game;
    this.world = game::getWorld;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.depth = 0;
  }

  public abstract void tick();

  public abstract void render(Graphics g);

  public void destroy() {
    game.removeEntity(this);
  }

  public boolean isColliding(Entity entity) {
    Rectangle e1m = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    Rectangle e2m = new Rectangle(getX(), getY(), getWidth(), getHeight());

    return e1m.intersects(e2m);
  }

  public Game getGame() {
    return game;
  }

  public World getWorld() {
    return world.get();
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setDepth(int toSet) {
    this.depth = toSet;
  }

  public int getX() {
    return (int) this.x;
  }

  public int getY() {
    return (int) this.y;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getDepth() {
    return this.depth;
  }

  public int getMaskX() {
    return this.maskX;
  }

  public int getMaskY() {
    return this.maskY;
  }

  public int getMaskWidth() {
    return this.maskWidth;
  }

  public int getMaskHeight() {
    return this.maskHeight;
  }

  public static Comparator<Entity> depthSorter = Comparator.comparingInt(Entity::getDepth);
}
