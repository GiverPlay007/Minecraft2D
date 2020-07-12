package me.giverplay.minecraft2D.world;

import static me.giverplay.minecraft2D.world.World.TILE_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.inventory.Material;

public class Tile
{
	private static Game game = Game.getGame();
	
	public static BufferedImage TILE_STONE = game.getSpritesheet().getSprite(0, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_GRASS = game.getSpritesheet().getSprite(TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_DIRT = game.getSpritesheet().getSprite(TILE_SIZE * 2, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_SAND = game.getSpritesheet().getSprite(TILE_SIZE * 3, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_BEDROCK = game.getSpritesheet().getSprite(TILE_SIZE * 4, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_STONE_BRICKS = game.getSpritesheet().getSprite(TILE_SIZE * 5, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_WOOD = game.getSpritesheet().getSprite(TILE_SIZE * 6, 0, TILE_SIZE, TILE_SIZE);
	public static BufferedImage TILE_BRICKS = game.getSpritesheet().getSprite(TILE_SIZE * 7, 0, TILE_SIZE, TILE_SIZE);
	
	private BufferedImage sprite;
	private Material type;
	
	private boolean isRigid;
	private boolean isFinal;
	
	private int x, y;
	
	public Tile(Material type, int x, int y, boolean isRigid, boolean isFinal, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.isRigid = isRigid;
		this.isFinal = isFinal;
		this.type = type;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x - game.getCamera().getX(), y - game.getCamera().getY(), null);
	}
	
	public Material getType()
	{
		return this.type;
	}
	
	public boolean isRigid()
	{
		return this.isRigid;
	}
	
	public boolean isFinal()
	{
		return this.isFinal;
	}
	
	public int getX()
	{
		return this.getX();
	}
	
	public int getY()
	{
		return this.getY();
	}
	
	public static Tile forMaterial(Material mat, int x, int y)
	{
		try
		{
			Class<?> clazz = mat.getTileClass();
			Constructor<?> cons = clazz.getConstructor(Integer.class, Integer.class);
			Object obj = cons.newInstance(x, y);
			
			return (Tile) obj;
		}
		catch(Exception e)
		{ System.out.println(e.getMessage());
			return null;
		}
	
	}
}
