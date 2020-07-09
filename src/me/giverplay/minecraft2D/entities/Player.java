package me.giverplay.minecraft2D.entities;

import static me.giverplay.minecraft2D.world.World.canMove;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.graphics.Camera;
import me.giverplay.minecraft2D.sound.Sound;

public class Player extends Entity
{
	private static final int DIR_RIGHT = 0;
	private static final int DIR_LEFT = 1;
	
	private static final int MAX_FRAMES_ANIM = 5;
	
	private boolean up, down, left, right;
	
	private boolean damaged = false;
	private boolean isJumping = false;
	private boolean jump = false;
	private boolean animChangeStage = false;
	private boolean canDamage = false;
	private boolean moving = false;
	
	private double gravity = 0.4;
	private double vspd = 0;
	
	private int undamageable = 0;
	private int maxVida = 5;
	private int vida = 5;
	private int anim = 0;
	private int anim_frames = 0;
	private int dir = 0;
	
	private Game game;
	private Camera camera;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 1, null);
		game = Game.getGame();
		camera = game.getCamera();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		if (vida == 0)
		{
			game.matar();
			return;
		}
		
		if (!canDamage)
		{
			undamageable++;
			
			if (undamageable >= 40)
			{
				undamageable = 0;
				canDamage = true;
			}
		}
		
		vspd += gravity;
		
		if (jump)
		{
			jump = false;
			
			if(!canMove(getX(), (int) (y + 1)) && canMove(getX(), (int) (y -1)))
			{
				vspd = -6;
				Sound.jump.play();
			}
		}
		
		if (!canMove((int) x, (int) (y + vspd)))
		{
			
			int signVsp = 0;
			
			if (vspd >= 0)
			{
				signVsp = 1;
			} else
			{
				signVsp = -1;
			}
			
			while (canMove((int) x, (int) (y + signVsp)))
			{
				y = y + signVsp;
			}
			
			vspd = 0;
		}
		
		y = y + vspd;
		
		moving = false;
		
		if (!(right && left))
		{
			if (right)
			{
				if (canMove((int) (x + speed), getY()))
				{
					moveX(speed);
					if (!isJumping)
						moving = true;
				}
				
			} else if (left)
			{
				if (canMove((int) (x - speed), getY()))
				{
					moveX(-speed);
					if (!isJumping)
						moving = true;
				}
			}
		}
		
		if (isJumping)
		{
			
		} else if (moving)
		{
			anim_frames++;
			
			if (anim_frames >= MAX_FRAMES_ANIM)
			{
				anim_frames = 0;
				
				if (!animChangeStage)
					anim++;
				else
					anim--;
				
				if (anim >= Entity.SPRITE_PLAYER_RIGHT.length - 1)
				{
					anim--;
					animChangeStage = !animChangeStage;
				} else if (anim < 0)
				{
					anim++;
					animChangeStage = !animChangeStage;
				}
			}
		}
		
		updateCamera();
	}
	
	private void updateCamera()
	{
		camera.setX(Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, game.getWorld().getWidth() * 16 - Game.WIDTH));
		camera.setY(Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, game.getWorld().getHeight() * 16 - Game.HEIGHT));
	}
	
	@Override
	public void render(Graphics g)
	{
		BufferedImage image = (dir == DIR_RIGHT ? SPRITE_PLAYER_RIGHT : SPRITE_PLAYER_LEFT)[vspd == 0 ? anim : 2]
				.getSubimage(0, 0, 16, 16);
		
		g.drawImage(image, getX() - camera.getX(), getY() - camera.getY(), null);
	}
	
	public boolean walkingRight()
	{
		return this.right;
	}
	
	public boolean walkingLeft()
	{
		return this.left;
	}
	
	public boolean walkingDown()
	{
		return this.down;
	}
	
	public boolean walkingUp()
	{
		return this.up;
	}
	
	public void setWalkingRight(boolean walking)
	{
		this.right = walking;
		this.dir = DIR_RIGHT;
		
		if (!walking && left)
			dir = DIR_LEFT;
	}
	
	public void setWalkingLeft(boolean walking)
	{
		this.left = walking;
		this.dir = DIR_LEFT;
		
		if (!walking && right)
			dir = DIR_RIGHT;
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
	
	public boolean isDamaged()
	{
		return this.damaged;
	}
	
	public void setDamaged(boolean toDamage)
	{
		if (toDamage && isJumping)
			return;
		
		this.damaged = toDamage;
	}
	
	public void handleJump()
	{
		if (!canMove(getX(), (int) (y + 1)))
			jump = true;
	}
	
	public void damage()
	{
		if (!canDamage)
			return;
		
		canDamage = false;
		
		vida--;
		
		if (vida < 0)
			vida = 0;
		
		Sound.hit.play();
	}
	
	public boolean canBeDamaged()
	{
		return this.canDamage;
	}
	
	public boolean fallingRelative()
	{
		return canMove(getX(), getY() + 1) && vspd >= 0;
	}
}
