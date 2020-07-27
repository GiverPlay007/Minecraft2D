package me.giverplay.minecraft2D.world;

import static me.giverplay.minecraft2D.world.World.TILE_SIZE;

import java.util.Random;

import me.giverplay.minecraft2D.algorithms.PerlinNoise;
import me.giverplay.minecraft2D.inventory.Material;

public class Generator
{
	private Tile[] tiles;
	
	private PerlinNoise perlin;
	
	private double seed;
	
	private int width;
	private int height;
	
	public Generator(int width, int height, double seed)
	{
		this.width = width;
		this.height = height;
		this.seed = seed;
	}
	
	public Tile[] getProceduralTiles()
	{
		generateTiles();
		validateNullTiles();
		validateCaves();
		validateOres();
		validateSurfaceTiles();
		validateOceanAndLakes();
		generateStructures();
		generateFurnitures();
		validateTileBounds();
		
		return tiles;
	}
	
	public Tile[] update(Tile[] changed)
	{
		for (int xx = 0; xx < width; xx++)
		{
			for (int yy = 0; yy < height; yy++)
			{
				int index = xx + yy * width;
				Tile til = changed[index];
				
				if(til != null)
				{
					this.tiles[index].setType(til.getType());
					this.tiles[index].setModified(true);
				}
			}
		}
		
		validateTileBounds();
		return this.tiles;
	}
	
	private void generateTiles()
	{
		perlin = new PerlinNoise(seed);
		tiles = new Tile[width * height];
		
		for (int xx = 0; xx < width; xx++)
		{
			for (int yy = 0; yy < height; yy++)
			{
				if (yy >= height - 62)
				{
					int noise = (int) (perlin.noise(xx) * 10);
					
					int y2 = yy;
					y2 += noise;
					
					if (y2 >= height)
						y2 = height - 1;
					
					tiles[xx + y2 * width] = new Tile(Material.STONE, xx * TILE_SIZE, y2 * TILE_SIZE);
				}
			}
		}
	}
	
	private void validateNullTiles()
	{
		for (int xx = 0; xx < width; xx++)
			for (int yy = 0; yy < height; yy++)
				if (tiles[xx + yy * width] == null)
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
		for (int xx = 0; xx < width; xx++)
		{
			for (int yy = 0; yy < height; yy++)
			{
				int index = xx + yy * width;
				int index2 = xx + (yy - 1) * width;
				
				try
				{
					if (tiles[index].getType() == Material.STONE && tiles[index2].getType() == Material.AIR)
					{
						tiles[index].setType(Material.GRASS);
						
						int c = 0;
						
						while(c < 3)
						{
							c++;
							
							tiles[xx + (yy + c) * width].setType(Material.DIRT);
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					continue;
				}
			}
		}
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
		Random rand = new Random();
		
		for (int xx = 0; xx < width; xx++)
		{
			for (int yy = 0; yy < height; yy++)
			{
				int index = xx + yy * width;
				
				if (yy == height - 1)
				{
					tiles[index].setType(Material.BEDROCK);
					
					if (rand.nextInt(101) < 30)
					{
						tiles[xx + (yy - 1) * width].setType(Material.BEDROCK);
					}
				}
				
				boolean b = validateBonds(xx, yy);
				
				if (b)
					tiles[xx + yy * width].setFinal(true);
			}
		}
	}
	
	private boolean validateBonds(int x, int y)
	{
		return x == width - 1 || x == 0 || y == 0 || y == height - 1;
	}

	public double getSeed()
	{
		return this.seed;
	}

	public void generateProceduralTiles()
	{
		getProceduralTiles();
	}
}
