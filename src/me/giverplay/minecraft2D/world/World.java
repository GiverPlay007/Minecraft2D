package me.giverplay.minecraft2D.world;

import java.awt.Graphics;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.algorithms.PerlinNoise;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.inventory.Material;

public class World
{
	public static final int TILE_SIZE = 16;
	
	private static Tile[] tiles;
	
	private static Game game;
	
	private PerlinNoise perlin;
	private Camera camera;
	
	private double seed;
	
	private int width;
	private int height;
	
	public World(int width, int height, double seed)
	{
		this.width = width;
		this.height = height;
		this.seed = seed;
		
		game = Game.getGame();
		camera = game.getCamera();
		
		generateTiles();
		validateNullTiles();
		validateCaves();
		validateOres();
		validateSurfaceTiles();
		validateOceanAndLakes();
		generateStructures();
		generateFurnitures();
		validateTileBounds();
	}
	
	public World(int width, int height, Tile[] tiles)
	{
		game = Game.getGame();
		camera = game.getCamera();
		
		this.width = width;
		this.height = height;
		
		World.tiles = new Tile[width * height];
		
		for(int xx = 0; xx < width; xx++)
		{
			for(int yy = 0; yy < height; yy++)
			{
				int index = xx + yy * width;
				int x = xx * TILE_SIZE;
				int y = yy * TILE_SIZE;
				Tile til = tiles[index];
				
				World.tiles[index] = til == null ? new Tile(Material.AIR, x, y, validateBonds(xx, yy)) : new Tile(til.getType(), x, y, validateBonds(xx, yy)); 
			}
		}
	}
	
	private void generateTiles()
	{
		perlin = new PerlinNoise(seed);
		tiles = new Tile[width * height];
		
		for(int xx = 0; xx < width; xx++)
		{
			for(int yy = 0; yy < height; yy++)
			{			
				if(yy >= height - 62)
				{
					int noise = (int) (perlin.noise(xx) * 10);
					
					int y2 = yy;
					y2 += noise;
					
					if(y2 >= height)
						y2 = height -1;
					
					tiles[xx + y2 * width] = new Tile(Material.STONE, xx * TILE_SIZE, y2 * TILE_SIZE);
				}
			}
		}
	}
	
	private void validateNullTiles()
	{
		for(int xx = 0; xx < width; xx++)
			for(int yy = 0; yy < height; yy++)
				if(tiles[xx + yy * width] == null)
					tiles[xx + yy * width] = new Tile(Material.AIR, xx * TILE_SIZE, yy * TILE_SIZE, validateBonds(xx, yy));
	}
	
	private void validateCaves()
	{
		// TODO
	}
	
	private void validateOres()
	{
		// TODO
	}
	
	private void validateSurfaceTiles()
	{
		// TODO
	}
	
	private void validateOceanAndLakes()
	{
		// TODO
	}
	
	private void generateStructures()
	{
		// TODO
	}
	
	private void generateFurnitures()
	{
		// TODO
	}
	
	public void validateTileBounds()
	{
		for(int xx = 0; xx < width; xx++)
		{
			for(int yy = 0; yy < height; yy++)
			{
				boolean b = validateBonds(xx, yy);
				
				if(b)
					tiles[xx + yy * width].setFinal(true);
			}
		}	
	}
	
	private boolean validateBonds(int x, int y)
	{
		return x == width - 1 || x == 0 || y == 0 || y == height -1;
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
	
	public static boolean moveAllowed(int xn, int yn, int width, int height)
	{
		if(yn -1 + height < 0 || yn -1 + height >= game.getWorld().getHeight() * TILE_SIZE || xn -1 + width < 0 || xn -1 + width >= game.getWorld().getWidth() * TILE_SIZE)
			return false;
		
		try
		{
			int x1 = xn / TILE_SIZE;
			int y1 = yn / TILE_SIZE;
			
			int x2 = (xn + width -1) / TILE_SIZE;
			int y2 = yn / TILE_SIZE;
			
			int x3 = xn / TILE_SIZE;
			int y3 = (yn + height -1) / TILE_SIZE;
			
			int x4 = (xn + width -1) / TILE_SIZE;
			int y4 = (yn + height -1) / TILE_SIZE;
			
			World world = Game.getGame().getWorld();
			Tile[] tiles = world.getTiles();
			
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
		if(yn < 0 || yn >= game.getWorld().getHeight() * TILE_SIZE || xn < 0 || xn >= game.getWorld().getWidth() * TILE_SIZE)
			return false;
		
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
	
	public static double calcFallDamage(int time, double coefficient)
	{
		return (coefficient * ((time / 60) ^ 2 / 2) * 5);
	}
}
