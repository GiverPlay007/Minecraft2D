package me.giverplay.minecraft2D.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.inventory.Material;

public class Tile
{
	private static Game game = Game.getGame();
	
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
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
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
		{
			return null;
		}
		
	}
	
	public static Tile forMaterial(Material mat)
	{
		try
		{
			Class<?> clazz = mat.getTileClass();
			Constructor<?> cons = clazz.getConstructor(Integer.class, Integer.class);
			Object obj = cons.newInstance(0, 0);
			
			return (Tile) obj;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static Tile forMaterial(Material mat, int x, int y, boolean b)
	{
		try
		{
			Class<?> clazz = mat.getTileClass();
			//Constructor<?> cons = clazz.getDeclaredConstructor(Integer.class, Integer.class, Boolean.class);
			Constructor<?> cons = clazz.getConstructors()[0];
			Object obj = cons.newInstance(x, y, b);
			
			return (Tile) obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
