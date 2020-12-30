package me.giverplay.minecraft2D.world;

import java.awt.Graphics;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.game.Camera;

public class World
{
	private final Tile[] tiles;
	
	private Game game;
	
	private Camera camera;
	private Generator gen;
	
	private int width;
	private int height;
	
	public World(Game game, int width, int height, double seed)
	{
		initWorld(game, width, height, seed);
		this.tiles = gen.getProceduralTiles();
	}
	
	public World(Game game, int width, int height, Tile[] tiles, double seed)
	{
		initWorld(game, width, height, seed);
		this.gen.generateProceduralTiles();
		this.tiles = gen.update(tiles);
	}
	
	private void initWorld(Game game, int width, int height, double seed)
	{
		this.width = width;
		this.height = height;
		
		this.game = game;
		this.camera = game.getCamera();
		this.gen = new Generator(width, height, seed);
	}
	
	public void render(Graphics g)
	{
		int xs = camera.getX() >> 4;
		int ys = camera.getY() >> 4;
		int xf = (camera.getX() + Game.WIDTH) >> 4;
		int yf = (camera.getY() + Game.HEIGHT) >> 4;
			
			for (int xx = xs; xx <= xf; xx++)
			{
				for (int yy = ys; yy <= yf; yy++)
				{
					
					if (xx < xs || yy < ys || xx >= width || yy >= height)
						continue;
					
					Tile tile = tiles[xx + yy * width];
					
					if (tile != null)
						tile.render(g, camera);
				}
			}
	}

	public boolean moveAllowed(int xn, int yn, int width, int height)
	{
		if(xn < 0 || xn + width >= getWidth() || yn < 0 || yn >= getHeight())
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

		return !(tiles[index1].isRigid() || tiles[index2].isRigid() || tiles[index3].isRigid()
						|| tiles[index4].isRigid());

	}

	public boolean moveAllowed(int xn, int yn)
	{
		return moveAllowed(xn, yn, Tile.SIZE, Tile.SIZE);
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

	public double getSeed()
	{
		return gen.getSeed();
	}
	
	public static double calcFallDamage(int time, double coefficient)
	{
		return (coefficient * ((time / 60) ^ 1) * 5);
	}
}
