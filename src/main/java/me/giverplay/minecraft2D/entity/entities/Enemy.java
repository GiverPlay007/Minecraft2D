package me.giverplay.minecraft2D.entity.entities;

import java.awt.Graphics;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entity.EntityLiving;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.world.World;

public class Enemy extends EntityLiving
{
	private Game game;
	
	private boolean changeDir = false;
	
	private int coeff;
	private int anim;
	private int maxAnim = 3;
	private int animF = 0;
	private int maxAnimF = 10;
	
	public Enemy(double x, double y, double speed)
	{
		super(x, y, width, height, speed, null);
		
		setDepth(1);
		
		coeff = random.nextInt(6);
		coeff = coeff == 1 ? 0 : coeff == 3 ? 2 : coeff == 5 ? 4 : coeff;	
		
		anim = coeff;
		maxAnim = coeff + 2;
		
		game = Game.getGame();
	}
	
	@Override
	public void tick()
	{
		if(isColliding(this, game.getPlayer()))
		{
			if(game.getPlayer().fallingRelative())
				destroy();
			else
				game.getPlayer().damage();
		}
		
		if (canMove(getX(), (int) (y + speed * 2)))
			moveY(speed * 2);
		
		if(canMove(getX() + (changeDir ? -World.TILE_SIZE : World.TILE_SIZE), (int) (y + 1))
				|| !canMove(getX() + (changeDir ? (int) -speed : (int) -speed), getY()))
		{
			changeDir = !changeDir;
		}
		
		moveX(changeDir ? -speed : speed);
	}
	
	@Override
	public void render(Graphics g)
	{
		animF++;
		
		if(animF >= maxAnimF)
		{
			anim++;
			animF = 0;
			
			if(anim >= maxAnim)
			{
				anim = coeff;
			}
		}
		
		//g.drawImage(SPRITE_ENEMY[anim], getX() - game.getCamera().getX(), getY() - game.getCamera().getY(), null);
	}
}
