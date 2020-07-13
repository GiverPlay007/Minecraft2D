package me.giverplay.minecraft2D.entities;

import static me.giverplay.minecraft2D.world.World.moveAllowed;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.GameMode;
import me.giverplay.minecraft2D.game.Listeners;
import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.sound.Sound;

public class Player extends LivingEntity
{
	private static final int DIR_RIGHT = 0;
	private static final int DIR_LEFT = 1;
	
	private static final int MAX_FRAMES_ANIM = 5;
	
	private boolean damaged = false;
	private boolean isJumping = false;
	private boolean jump = false;
	private boolean animChangeStage = false;
	private boolean canDamage = false;
	private boolean moving = false;
	private boolean right, left;
	private boolean caiu = false;
	
	private double gravity = 0.4;
	private double vspd = 0;
	private double fallingCoefficient = 0.009;
	private double take = 0D;
	
	private int fallingFrames = 0;
	private int maxFallingFrames = 20;
	private int undamageable = 0;
	private int anim = 0;
	private int anim_frames = 0;
	private int dir = 0;
	
	private Game game;
	private Camera camera;
	private Inventory inv;
	private GameMode mode;
	private Listeners input;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 1, null);
		
		setGamemode(GameMode.SURVIVAL);
		
		game = Game.getGame();
		camera = game.getCamera();
		input = game.getListeners();
		inv = new PlayerInventory(36, this);
		
		((PlayerInventory) inv).resetDefaults();
		
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
			
			if(!moveAllowed(getX() + mx, (int) (y + 1) + my, mw, mh) && moveAllowed(getX() + mx, (int) (y -1) + my, mw, mh))
			{
				vspd = -4;
			}
		}
		
		if (!moveAllowed((int) x + mx, (int) (y + vspd) + my, mw, mh))
		{
			
			int signVsp = 0;
			
			if (vspd >= 0)
			{
				signVsp = 1;
			} else
			{
				signVsp = -1;
			}
			
			while (moveAllowed((int) x + mx, (int) (y + signVsp) + my, mw, mh))
			{
				y = y + signVsp;
			}
			
			vspd = 0;
		}
		
		if(vspd > 0) // TODO AQUI
		{
			fallingFrames++;
			
			if(fallingFrames >= maxFallingFrames)
			{
				caiu = true;
				take = Math.round(vspd * fallingFrames * fallingCoefficient);
			}
		}
		else
		{
			fallingFrames = 0;
			
			if(caiu)
			{
				caiu = false;
				System.out.println(take);
			}
		}
		
		y = y + vspd;
		
		short xa = 0;
		
		if(input.right.down)
			xa++;
		
		if(input.left.down)
			xa--;
		
		right = xa > 0;
		left = xa < 0;
		
		moving = right || left;
		jump = input.jump.down;
		
		if (right)
		{
			if (moveAllowed((int) (x + speed) + mx, getY() + my, mw, mh))
			{
				moveX(speed);
				dir = DIR_RIGHT;
			}
		} 
		else if (left)
		{
			if (moveAllowed((int) (x - speed) + mx, getY() + my, mw, mh))
			{
				moveX(-speed);
				dir = DIR_LEFT;
			}
		}
		
		if (moving)
		{
			anim_frames++;
			
			if (anim_frames >= MAX_FRAMES_ANIM)
			{
				anim_frames = 0;
				
				if (!animChangeStage)
					anim++;
				else
					anim--;
				
				if (anim >= Spritesheet.SPRITE_PLAYER_RIGHT.length)
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
	
	public void updateCamera()
	{
		camera.setX(Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, game.getWorld().getWidth() * 16 - Game.WIDTH));
		camera.setY(Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, game.getWorld().getHeight() * 16 - Game.HEIGHT));
	}
	
	@Override
	public void render(Graphics g)
	{
		BufferedImage image = (dir == DIR_RIGHT ? Spritesheet.SPRITE_PLAYER_RIGHT : Spritesheet.SPRITE_PLAYER_LEFT)[anim]
				.getSubimage(0, 0, 16, 16);
		
		g.drawImage(image, getX() - camera.getX(), getY() - camera.getY(), null);
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
		return moveAllowed(getX() + mx, getY() + 1 + my, mw, mh) && vspd >= 0;
	}
	
	public Inventory getInventory()
	{
		return this.inv;
	}
	
	public GameMode getGamemode()
	{
		return mode;
	}
	
	public void setGamemode(GameMode mode)
	{
		this.mode = mode;
	}
	
	public void setInventory(PlayerInventory inv)
	{
		this.inv = inv;
	}
	
	public void setLife(int life)
	{
		this.vida = life;
	}
	
	public void setMaxLife(int life)
	{
		this.maxVida = life;
	}
}
