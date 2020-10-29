package me.giverplay.minecraft2D.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.Game;

public class Tile
{
	private static final Game game = Game.getGame();
	
	private final int x;
	private final int y;
	
	private String name;
	private BufferedImage sprite;
	private Material type;
	
	private boolean isRigid;
	private boolean isFinal;
	private boolean creativeOnly;
	private boolean modified;
	
	public Tile(Material type)
	{
		this(type, 0, 0);
	}
	
	public Tile(Material type, int x, int y)
	{
		this(type, x, y, false);
	}
	
	public Tile(Material type, int x, int y, boolean isFinal)
	{
		setType(type);
		
		this.isFinal = isFinal;
		this.x = x;
		this.y = y;
	}
	
	public void setType(Material type)
	{
		this.type = type;
		this.name = type.getName();
		this.sprite = type.getSprite();
		this.isRigid = type.isRigid();
		this.creativeOnly = type.isCreativeOnly();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x - game.getCamera().getX(), y - game.getCamera().getY(), null);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Material getType()
	{
		return this.type;
	}
	
	public boolean isRigid()
	{
		return this.isRigid;
	}
	
	public void setFinal(boolean set)
	{
		this.isFinal = set;
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
	
	public boolean creativeOnly()
	{
		return this.creativeOnly;
	}
	
	public boolean modified()
	{
		return this.modified;
	}
	
	public void setModified(boolean toSet)
	{
		this.modified = toSet;
	}
}
