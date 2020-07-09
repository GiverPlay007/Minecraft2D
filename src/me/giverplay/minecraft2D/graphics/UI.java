package me.giverplay.minecraft2D.graphics;

import static me.giverplay.minecraft2D.Game.HEIGHT;
import static me.giverplay.minecraft2D.Game.SCALE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entities.Entity;

public class UI
{
	private Game game;
	
	public UI()
	{
		game = Game.getGame();
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.setFont(FontUtils.getFont(16, Font.PLAIN));
		
		int max = game.getPlayer().getMaxLife();
		int cur = game.getPlayer().getLife();
		int coe = 24;
		
		for(int i = 0; i < max; i++)
		{
			g.drawImage(i < cur ? Entity.SPRITE_LIFE_FULL : Entity.SPRITE_LIFE_NON_FULL, i * (coe + 5) + 5, HEIGHT * SCALE - coe -5, coe, coe, null);
		}
		
		renderInventory(g);
	}
	
	private void renderInventory(Graphics g)
	{
		
	}
}
