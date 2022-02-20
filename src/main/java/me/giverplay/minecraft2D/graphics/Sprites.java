package me.giverplay.minecraft2D.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static me.giverplay.minecraft2D.world.Tile.SIZE;

public final class Sprites {

  public static BufferedImage TILE_STONE;
  public static BufferedImage TILE_GRASS;
  public static BufferedImage TILE_DIRT;
  public static BufferedImage TILE_SAND;
  public static BufferedImage TILE_BEDROCK;
  public static BufferedImage TILE_STONE_BRICKS;
  public static BufferedImage TILE_WOOD;
  public static BufferedImage TILE_BRICKS;

  public static BufferedImage[] SPRITE_PLAYER_RIGHT;
  public static BufferedImage[] SPRITE_PLAYER_LEFT;

  public static BufferedImage ICON_LIFE_FULL;
  public static BufferedImage ICON_LIFE_NON_FULL;

  public static BufferedImage ICON_BUTTON;
  public static BufferedImage ICON_SEL;

  private static BufferedImage SPRITESHEET;

  public static BufferedImage getSprite(int x, int y, int width, int hight) {
    return SPRITESHEET.getSubimage(x, y, width, hight);
  }

  public static void init() throws IOException {
    SPRITESHEET = ImageIO.read(Sprites.class.getResource("/Spritesheet.png"));

    TILE_STONE = getSprite(0, 0, SIZE, SIZE);
    TILE_GRASS = getSprite(SIZE, 0, SIZE, SIZE);
    TILE_DIRT = getSprite(SIZE * 2, 0, SIZE, SIZE);
    TILE_SAND = getSprite(SIZE * 3, 0, SIZE, SIZE);
    TILE_BEDROCK = getSprite(SIZE * 4, 0, SIZE, SIZE);
    TILE_STONE_BRICKS = getSprite(SIZE * 5, 0, SIZE, SIZE);
    TILE_WOOD = getSprite(SIZE * 6, 0, SIZE, SIZE);
    TILE_BRICKS = getSprite(SIZE * 7, 0, SIZE, SIZE);

    SPRITE_PLAYER_RIGHT = new BufferedImage[3];
    SPRITE_PLAYER_LEFT = new BufferedImage[3];

    for (int i = 0; i < 3; i++) {
      SPRITE_PLAYER_RIGHT[i] = getSprite(i * (SIZE * 2), 128, SIZE * 2, SIZE * 2);
      SPRITE_PLAYER_LEFT[i] = getSprite(i * (SIZE * 2), 96, SIZE * 2, SIZE * 2);
    }

    ICON_LIFE_FULL = getSprite(SIZE * 4, SIZE * 4, SIZE, SIZE);
    ICON_LIFE_NON_FULL = getSprite(SIZE * 5, SIZE * 4, SIZE, SIZE);
    ICON_BUTTON = getSprite(SIZE * 3, SIZE * 4, SIZE, SIZE / 2);
    ICON_SEL = getSprite(SIZE * 3, SIZE * 4 + SIZE / 2, SIZE, SIZE / 2);
  }
}
