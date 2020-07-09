package me.giverplay.minecraft2D.world;

import java.awt.Graphics;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.graphics.Camera;

public class World
{
	public static final int TILE_SIZE = 16;
	
	private static Tile[] tiles;
	
	private Game game;
	private Camera camera;
	
	private int width;
	private int height;
	
	public World(int width, int height)
	{
		game = Game.getGame();
		camera = game.getCamera();
		
		initializeWorld(width, height);
	}
	
	private void initializeWorld(int width, int height)
	{
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		
		for(int xx = 0; xx < width; xx++)
		{
			for(int yy = 0; yy < height; yy++)
			{
				int index = xx + yy * width;
				
				tiles[index] = yy > 10 ? new FloorTile(xx * 16, yy * 16, true) : new AirTile(xx * 16, yy * 16);
			}
		}
	}
	
	public void render(Graphics g)
	{
		int xs = camera.getX() >> 4;
		int ys = camera.getY() >> 4;
		int xf = (camera.getX() + Game.WIDTH) >> 4;
		int yf = (camera.getY() + Game.HEIGHT) >> 4;
		
		
		for(int xx = xs; xx <= xf; xx++)
		{
			for(int yy = ys; yy <= yf; yy++)
			{
				
				if(xx < xs || yy < ys || xx >= width || yy >= height)
					continue;
				
				Tile tile = tiles[xx + yy * width];
				
				if(tile != null)
					tile.render(g);
			}
		}
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Tile[] getTiles()
	{
		return tiles;
	}
	
	public static boolean canMove(int xn, int yn)
	{
		try
		{
			int x1 = xn / TILE_SIZE;
			int y1 = yn / TILE_SIZE;
			
			int x2 = (xn + TILE_SIZE -1) / TILE_SIZE;
			int y2 = yn / TILE_SIZE;
			
			int x3 = xn / TILE_SIZE;
			int y3 = (yn + TILE_SIZE -1) / TILE_SIZE;
			
			int x4 = (xn + TILE_SIZE -1) / TILE_SIZE;
			int y4 = (yn + TILE_SIZE -1) / TILE_SIZE;
			
			World world = Game.getGame().getWorld();
			
			int index1 = x1 + (y1 * world.getWidth());
			int index2 = x2 + (y2 * world.getWidth());
			int index3 = x3 + (y3 * world.getWidth());
			int index4 = x4 + (y4 * world.getWidth());
			
			return !(tiles[index1].isRigid()
					|| tiles[index2].isRigid()
					|| tiles[index3].isRigid()
					|| tiles[index4].isRigid());
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
	}
}
