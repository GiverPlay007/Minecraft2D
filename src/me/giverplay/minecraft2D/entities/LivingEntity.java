package me.giverplay.minecraft2D.entities;

import java.awt.image.BufferedImage;

public class LivingEntity extends Entity
{
	protected int maxVida = 10;
	protected int vida = 10;
	protected int mx = 6;
	protected int my = 2;
	protected int mw = 4;
	protected int mh = 14;
	
	public LivingEntity(double x, double y, int width, int height, double speed, BufferedImage sprite)
	{
		super(x, y, width, height, speed, sprite);
	}
	
	public int getLife()
	{
		return vida;
	}
	
	public void modifyLife(int toModify)
	{
		vida += toModify;
		
		if (vida < 0)
			vida = 0;
		if (vida > maxVida)
			vida = maxVida;
	}
	
	public int getMaxLife()
	{
		return this.maxVida;
	}
	
	public int getMaskX()
	{
		return this.mx;
	}
	
	public int getMaskY()
	{
		return this.my;
	}
	
	public int getMaskWidth()
	{
		return this.mw;
	}
	
	public int getMaskHeight()
	{
		return this.mh;
	}
}
