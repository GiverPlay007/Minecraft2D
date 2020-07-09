package me.giverplay.minecraft2D.graphics;

import static me.giverplay.minecraft2D.Game.HEIGHT;
import static me.giverplay.minecraft2D.Game.SCALE;
import static me.giverplay.minecraft2D.Game.WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.inventory.Item;

public class UI
{
	private ArrayList<Toast> toasts = new ArrayList<>();
	
	private Game game;
	private Toast toast;
	private Color currentColor;
	
	private boolean showingToast = false;
	
	private int toastFadeIn = 0;
	private int toastFadeOut = 0;
	private int toastFrames = 0;
	private int maxToastFrames = 0;
	private int time = 0;
	
	private int slotSize = 32;
	private int xs = (WIDTH * SCALE) / 2 - 9 * slotSize / SCALE;
	private int ys = (HEIGHT * SCALE) - slotSize - 5;
	
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
		advanceToast(g);
	}
	
	private void renderInventory(Graphics g)
	{
		g.setColor(new Color(0, 0, 0, 144));
		g.fillRect(xs, ys, 9 * slotSize, slotSize);
		g.setColor(Color.BLACK);
		
		for(int i = 0; i < 9; i++)
		{
			g.drawRect(xs + i * slotSize, ys, slotSize, slotSize);
			
			Item item = game.getPlayer().getInventory().getItem(i);
			
			if(item != null)
				g.drawImage(item.getSprite(), xs + i * slotSize + 4, ys + 4, 24, 24, null);
		}
	}
	
	public void advanceToast(Graphics g)
	{
		if(!showingToast)
		{
			if(toasts.size() == 0)
				return;
			
			toast = toasts.get(0);
			showingToast = true;
			toastFadeIn = toast.getFadeIn();
			toastFadeOut = toast.getFadeOut();
			maxToastFrames = toastFadeIn + toastFadeOut + time;
		}
		
		toastFrames++;
		
		if(toastFrames >= maxToastFrames)
		{
			toasts.remove(0);
			toast = null;
			showingToast = false;
			toastFrames = 0;
			
			return;
		}
		
		int alpha = 255;
		
		if(toastFrames <= toastFadeIn)
		{
			alpha = (int) (255 * toastFrames / toastFadeIn);
		}
		else if(toastFrames >= toastFadeIn + time)
		{
			alpha = (int) (255 * ( toastFadeOut - (toastFrames - (toastFadeIn + time))) / toastFadeOut);
		}
		
		currentColor = new Color(255, 255, 255, alpha);
		g.setColor(currentColor);
		
		g.setFont(FontUtils.getFont(32, Font.BOLD));
		int width = FontUtils.stringWidth(g, toast.getText());
		
		g.drawString(toast.getText(), (Game.WIDTH - width) / 2, Game.HEIGHT / 2);
	}
	
	public void addToast(Toast toast)
	{
		toasts.add(toast);
	}
}
