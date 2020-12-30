package me.giverplay.minecraft2D.entity.entities;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entity.EntityMob;
import me.giverplay.minecraft2D.graphics.Animation;
import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.world.Tile;

import java.awt.Graphics;

public class EnemyEntity extends EntityMob
{
	private final Animation animation;

	public EnemyEntity(Game game, double x, double y)
	{
		super(game, x, y, Tile.SIZE, Tile.SIZE, 5, 1);

		animation = new Animation(Sprites.SPRITE_PLAYER_LEFT, 250);
		setDepth(1);
	}
	
	@Override
	public void tick()
	{
		super.tick();

		if(isColliding(game.getPlayer()))
		{
			if(RANDOM.nextInt(100) < 20)
				game.getPlayer().damage(1);
		}
		
		if(movingRight)
		{
			if(!isMoveAllowed((int) (x + speed), getY()))
			{
				movingRight = false;
				movingLeft = true;
			}
		}

		if(movingLeft)
		{
			if(!isMoveAllowed((int) (x - speed), getY()))
			{
				movingLeft = false;
				movingRight = true;
			}
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		g.drawImage(animation.getFrame(), getX() - game.getCamera().getX(), getY() - game.getCamera().getY(), getWidth(), getHeight(), null);
	}
}
