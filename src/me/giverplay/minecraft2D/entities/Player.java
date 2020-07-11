package me.giverplay.minecraft2D.entities;

import static me.giverplay.minecraft2D.world.World.moveAllowed;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.inventory.items.BedrockItem;
import me.giverplay.minecraft2D.inventory.items.BricksItem;
import me.giverplay.minecraft2D.inventory.items.DirtItem;
import me.giverplay.minecraft2D.inventory.items.GrassItem;
import me.giverplay.minecraft2D.inventory.items.SandItem;
import me.giverplay.minecraft2D.inventory.items.StoneBricksItem;
import me.giverplay.minecraft2D.inventory.items.StoneItem;
import me.giverplay.minecraft2D.inventory.items.WoodItem;
import me.giverplay.minecraft2D.sound.Sound;

public class Player extends LivingEntity
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
	private int anim = 0;
	private int anim_frames = 0;
	private int dir = 0;
	
	private Game game;
	private Camera camera;
	private Inventory inv;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height, 1, null);
		game = Game.getGame();
		camera = game.getCamera();
		inv = new PlayerInventory(36, this);
		
		inv.addItem(new StoneItem(64));
		inv.addItem(new GrassItem(64));
		inv.addItem(new DirtItem(64));
		inv.addItem(new SandItem(64));
		inv.addItem(new BedrockItem(64));
		inv.addItem(new StoneBricksItem(64));
		inv.addItem(new WoodItem(64));
		inv.addItem(new BricksItem(64));
		
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
		
		y = y + vspd;
		
		moving = false;
		
		if (!(right && left))
		{
			if (right)
			{
				if (moveAllowed((int) (x + speed) + mx, getY() + my, mw, mh))
				{
					moveX(speed);
					if (!isJumping)
						moving = true;
				}
				
			} else if (left)
			{
				if (moveAllowed((int) (x - speed) + mx, getY() + my, mw, mh))
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
				
				if (anim >= Entity.SPRITE_PLAYER_RIGHT.length)
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
		BufferedImage image = (dir == DIR_RIGHT ? SPRITE_PLAYER_RIGHT : SPRITE_PLAYER_LEFT)[anim]
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
		if (!moveAllowed(getX() + mx, (int) (y + 1) + my, mw, mh))
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
		return moveAllowed(getX() + mx, getY() + 1 + my, mw, mh) && vspd >= 0;
	}
	
	public Inventory getInventory()
	{
		return this.inv;
	}
}
