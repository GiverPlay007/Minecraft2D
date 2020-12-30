package me.giverplay.minecraft2D.entity.entities;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entity.EntityMob;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.GameInput;
import me.giverplay.minecraft2D.game.GameMode;
import me.giverplay.minecraft2D.graphics.Animation;
import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.world.Tile;

import java.awt.Graphics;

public class PlayerEntity extends EntityMob
{
	private final Animation animation;
	private final GameInput input;
	private final Camera camera;

	private Inventory inv;
	private GameMode mode;

	public PlayerEntity(Game game, int x, int y)
	{
		super(game, x, y, Tile.SIZE, Tile.SIZE, 10, 2);
		
		setGameMode(GameMode.SURVIVAL);

		animation = new Animation(Sprites.SPRITE_PLAYER_RIGHT, 300);
		camera = game.getCamera();
		input = game.getInput();
		inv = new PlayerInventory(36, this);
		
		((PlayerInventory) inv).resetDefaults();
		
		setDepth(2);
	}
	
	@Override
	public void tick()
	{
		if (getLife() <= 0)
		{
			game.doGameOver();
			return;
		}

		if(input.jump.down)
			jump();
		
		updateCamera();
	}
	
	public void updateCamera()
	{
		camera.setX(Camera.clamp(getX() - (Game.WIDTH / 2), 0, world.getWidth() * Tile.SIZE - Game.WIDTH));
		camera.setY(Camera.clamp(getY() - (Game.HEIGHT / 2), 0, world.getHeight() * Tile.SIZE - Game.HEIGHT));
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(animation.getFrame(), getX() - camera.getX(), getY() - camera.getY(), getWidth(), getHeight(), null);
	}
	
	public Inventory getInventory()
	{
		return this.inv;
	}
	
	public GameMode getGameMode()
	{
		return mode;
	}
	
	public void setGameMode(GameMode mode)
	{
		this.mode = mode;
	}
	
	public void setInventory(PlayerInventory inv)
	{
		this.inv = inv;
	}
}
