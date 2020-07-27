package me.giverplay.minecraft2D.graphics;

import static me.giverplay.minecraft2D.world.World.TILE_SIZE;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet
{
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
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	{
		try
		{
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e)
		{
			System.out.println("Erro na leitura do Spritesheet");
		}
		
		setup();
	}
	
	public BufferedImage getSprite(int x, int y, int width, int hight)
	{
		return spritesheet.getSubimage(x, y, width, hight);
	}
	
	private void setup()
	{
		TILE_STONE = getSprite(0, 0, TILE_SIZE, TILE_SIZE);
		TILE_GRASS = getSprite(TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
		TILE_DIRT = getSprite(TILE_SIZE * 2, 0, TILE_SIZE, TILE_SIZE);
		TILE_SAND = getSprite(TILE_SIZE * 3, 0, TILE_SIZE, TILE_SIZE);
		TILE_BEDROCK = getSprite(TILE_SIZE * 4, 0, TILE_SIZE, TILE_SIZE);
		TILE_STONE_BRICKS = getSprite(TILE_SIZE * 5, 0, TILE_SIZE, TILE_SIZE);
		TILE_WOOD = getSprite(TILE_SIZE * 6, 0, TILE_SIZE, TILE_SIZE);
		TILE_BRICKS = getSprite(TILE_SIZE * 7, 0, TILE_SIZE, TILE_SIZE);
		
		SPRITE_PLAYER_RIGHT = new BufferedImage[3];
		SPRITE_PLAYER_LEFT = new BufferedImage[3];
		
		for(int i = 0; i < 3; i++)
		{
			SPRITE_PLAYER_RIGHT[i] = getSprite(i * (TILE_SIZE * 2), 128, TILE_SIZE * 2, TILE_SIZE * 2);
			SPRITE_PLAYER_LEFT[i] = getSprite(i * (TILE_SIZE * 2), 96, TILE_SIZE * 2, TILE_SIZE * 2);
		}
		
		ICON_LIFE_FULL = getSprite(TILE_SIZE * 4, TILE_SIZE * 4, TILE_SIZE, TILE_SIZE);
		ICON_LIFE_NON_FULL = getSprite(TILE_SIZE * 5, TILE_SIZE * 4, TILE_SIZE, TILE_SIZE);
		ICON_BUTTON = getSprite(TILE_SIZE * 3, TILE_SIZE * 4, TILE_SIZE, TILE_SIZE / 2);
		ICON_SEL = getSprite(TILE_SIZE * 3, TILE_SIZE * 4 + TILE_SIZE / 2, TILE_SIZE, TILE_SIZE / 2);
	}
}
