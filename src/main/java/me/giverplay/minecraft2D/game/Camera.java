package me.giverplay.minecraft2D.game;

public class Camera
{
	private int x;
	private int y;
	
	public Camera(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public static int clamp(int atual, int min, int max)
	{
		if(atual < min)
		  atual = min;
		
		if(atual > max)
			atual = max;
		
		return atual;
	}
}
