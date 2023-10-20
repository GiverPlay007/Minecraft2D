package me.giverplay.minecraft2D.world;

import me.giverplay.minecraft2D.algorithms.PerlinNoise;

import java.util.Random;

public class Generator {

  private final World world;

  private final double seed;

  private final int width;
  private final int height;

  private int[] tiles;
  private int[] data;

  private PerlinNoise perlin;

  public Generator(World world, int width, int height, double seed) {
    this.world = world;
    this.width = width;
    this.height = height;
    this.seed = seed;
  }

  public void getProceduralTiles() {
    generateTiles();
    validateCaves();
    validateOres();
    validateSurfaceTiles();
    validateOceanAndLakes();
    generateStructures();
    generateFurniture();
    validateTileBounds();
  }

  public void updateChangedTiles(int[] changedTiles, int[] data) {
    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int index = xx + yy * width;
        int tileId = changedTiles[index];

        if (tileId != -1) {
          this.tiles[index] = tileId;
          this.data[index] = data[index];
        }
      }
    }
  }

  private void generateTiles() {
    perlin = new PerlinNoise(seed);
    tiles = new int[width * height];
    data = new int[width * height];

    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        if (yy >= height - 62) {
          int noise = (int) (perlin.noise(xx) * 10);

          int y2 = yy;
          y2 += noise;

          if (y2 >= height)
            y2 = height - 1;

          tiles[xx + y2 * width] = Material.STONE.id;
        }
      }
    }
  }

  private void validateCaves() {
    // TODO
  }

  private void validateOres() {
    // TODO
  }

  private void validateSurfaceTiles() {
    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int index = xx + yy * width;
        int index2 = xx + (yy - 1) * width;

        try {
          if (tiles[index] == Material.STONE.id && tiles[index2] == Material.AIR.id) {
            tiles[index] = Material.GRASS.id;

            int c = 0;

            while (c < 3) {
              c++;

              tiles[xx + (yy + c) * width] = Material.DIRT.id;
            }
          }
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
      }
    }
  }

  private void validateOceanAndLakes() {
    // TODO
  }

  private void generateStructures() {
    // TODO
  }

  private void generateFurniture() {
    // TODO
  }

  public void validateTileBounds() {
    Random rand = new Random((long) seed);

    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int index = xx + yy * width;

        if (yy == height - 1) {
          tiles[index] = Material.BEDROCK.id;

          if (rand.nextInt(101) < 30) {
            tiles[xx + (yy - 1) * width] = Material.BEDROCK.id;
          }
        }

        boolean isBound = validateBonds(xx, yy);

        if (isBound) data[xx + yy * width] |= TileProperty.PERMANENT;
      }
    }
  }

  private boolean validateBonds(int x, int y) {
    return x == width - 1 || x == 0 || y == 0 || y == height - 1;
  }

  public int[] getTiles() {
    return tiles;
  }

  public int[] getTilesData() {
    return data;
  }

  public double getSeed() {
    return this.seed;
  }
}
