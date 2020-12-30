package me.giverplay.minecraft2D.entity;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.algorithms.Node;
import me.giverplay.minecraft2D.algorithms.Vector2i;
import me.giverplay.minecraft2D.world.Tile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public abstract class Entity
{
	protected final Game game;
	
	protected List<Node> path;
	protected static Random random = new Random();
	
	protected double x;
	protected double y;

	protected int maskX = 6;
	protected int maskY = 2;
	protected int maskWidth = 4;
	protected int masHeight = 14;

	protected final int width;
	protected final int height;
	protected int depth;
	
	public Entity(Game game, double x, double y, int width, int height)
	{
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.depth = 0;
	}
	
	public abstract void tick();

	public abstract void render(Graphics g);
	
	public void destroy()
	{
		game.removeEntity(this);
	}
	
	public void followPath(List<Node> path)
	{
		if(path != null)
		{
			if(path.size() > 0)
			{
				Vector2i target = path.get(path.size() - 1).tile;
				
				if(x < target.x * Tile.SIZE)
				{
					x++;
				}
				else if(x > target.x * Tile.SIZE)
				{
					x--;
				}
				
				if(y < target.y * Tile.SIZE)
				{
					y++;
				}
				else if(y > target.y * Tile.SIZE)
				{
					y--;
				}
				
				if(x == target.x * Tile.SIZE && y == target.y * Tile.SIZE)
					path.remove(path.size() -1);
			}
		}
	}
	
	public boolean isColliding(Entity entity)
	{
		Rectangle e1m = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
		Rectangle e2m = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		return e1m.intersects(e2m);
	}

	public Game getGame()
	{
		return game;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setDepth(int toSet)
	{
		this.depth = toSet;
	}

	public void moveX(double d)
	{
		x += d;
	}

	public void moveY(double d)
	{
		y += d;
	}

	public int getX()
	{
		return (int) this.x;
	}

	public int getY()
	{
		return (int) this.y;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public int getDepth()
	{
		return this.depth;
	}

	public int getMaskX()
	{
		return this.maskX;
	}

	public int getMaskY()
	{
		return this.maskY;
	}

	public int getMaskWidth()
	{
		return this.maskWidth;
	}

	public int getMaskHeight()
	{
		return this.masHeight;
	}

	public static Comparator<Entity> depthSorter = Comparator.comparingInt(Entity::getDepth);

	public static double pointDistance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + ((y2 - y1) * (y2 - y1)));
	}
}
