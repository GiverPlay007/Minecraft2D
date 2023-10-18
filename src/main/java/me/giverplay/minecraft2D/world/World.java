package me.giverplay.minecraft2D.world;

import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.Game;

import java.awt.Graphics;

public class World {

  private final int[] tiles;
  private final int[] data;

  private final Camera camera;
  private final Generator generator;

  private final int width;
  private final int height;

  private long gameTime;

  public World(Game game, int width, int height, double seed) {
    this(game, width, height, null, null, seed);
  }

  public World(Game game, int width, int height, int[] tiles, int[] tilesData, double seed) {
    this.width = width;
    this.height = height;

    this.camera = game.getCamera();
    this.generator = new Generator(this, width, height, seed);

    if (tiles != null) {
      generator.getProceduralTiles();
      generator.updateChangedTiles(tiles, tilesData);
      this.tiles = generator.getTiles();
      this.data = generator.getTilesData();
    } else {
      generator.getProceduralTiles();
      this.tiles = generator.getTiles();
      this.data = generator.getTilesData();
    }
  }

  public void makePermanentTile(int x, int y) {
    makePermanentTile(x + y * width);
  }

  public void makePermanentTile(int index) {
    addProperty(index, TileProperty.PERMANENT);
  }

  void addProperty(int x, int y, int prop) {
    addProperty(x + y * width, prop);
  }

  void addProperty(int index, int prop) {
    data[index] |= prop;
  }

  public void makeChangedTile(int x, int y) {
    makeChangedTile(x + y * width);
  }

  public void makeChangedTile(int index) {
    addProperty(index, TileProperty.CHANGED);
  }

  public boolean isPermanentTile(int index) {
    return checkProperty(index, TileProperty.PERMANENT);
  }

  public boolean isPermanentTile(int x, int y) {
    return checkProperty(x, y, TileProperty.PERMANENT);
  }

  public boolean isTileChanged(int index) {
    return checkProperty(index, TileProperty.CHANGED);
  }

  public boolean isTileChanged(int x, int y) {
    return checkProperty(x, y, TileProperty.CHANGED);
  }

  boolean checkProperty(int x, int y, int prop) {
    return checkProperty(x + y * width, prop);
  }

  boolean checkProperty(int index, int prop) {
    return (data[index] & prop) != 0;
  }

  public boolean moveAllowed(int xn, int yn, int width, int height) {
    if (xn < 0 || (xn + width - 1) / Tile.SIZE >= getWidth() || yn < 0 || (yn + height - 1) / Tile.SIZE >= getHeight())
      return false;

    int x1 = xn / Tile.SIZE;
    int y1 = yn / Tile.SIZE;

    int x2 = (xn + width - 1) / Tile.SIZE;
    int y2 = yn / Tile.SIZE;

    int x3 = xn / Tile.SIZE;
    int y3 = (yn + height - 1) / Tile.SIZE;

    int x4 = (xn + width - 1) / Tile.SIZE;
    int y4 = (yn + height - 1) / Tile.SIZE;

    int index1 = x1 + (y1 * getWidth());
    int index2 = x2 + (y2 * getWidth());
    int index3 = x3 + (y3 * getWidth());
    int index4 = x4 + (y4 * getWidth());

    return !(getTile(index1).isRigid()
      || getTile(index2).isRigid()
      || getTile(index3).isRigid()
      || getTile(index4).isRigid());
  }

  public void render(Graphics graphics) {
    int xs = camera.getX() / Tile.SIZE;
    int ys = camera.getY() / Tile.SIZE;
    int xf = (camera.getX() + Game.WIDTH) / Tile.SIZE;
    int yf = (camera.getY() + Game.HEIGHT) / Tile.SIZE;

    for (int xx = xs; xx <= xf; xx++) {
      for (int yy = ys; yy <= yf; yy++) {
        if (xx < xs || yy < ys || xx >= width || yy >= height)
          continue;

        Tile tile = getTile(xx, yy);
        tile.render(graphics, xx, yy, camera);
      }
    }
  }

  public int[] getTiles() {
    return tiles;
  }

  public int[] getTilesData() {
    return data;
  }

  private Tile getTile(int index) {
    return Tiles.getById(tiles[index]);
  }

  public Tile getTile(int x, int y) {
    return Tiles.getById(tiles[x + y * width]);
  }

  public boolean moveAllowed(int xn, int yn) {
    return moveAllowed(xn, yn, Tile.SIZE, Tile.SIZE);
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public double getSeed() {
    return generator.getSeed();
  }

  public long getGameTime() {
    return gameTime;
  }

  public void setGameTime(long gameTime) {
    this.gameTime = gameTime;
  }
}
