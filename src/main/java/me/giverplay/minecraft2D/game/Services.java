package me.giverplay.minecraft2D.game;

import static me.giverplay.minecraft2D.Game.HEIGHT;
import static me.giverplay.minecraft2D.Game.SCALE;
import static me.giverplay.minecraft2D.Game.WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.graphics.FontUtils;
import me.giverplay.minecraft2D.graphics.FutureRender;
import me.giverplay.minecraft2D.graphics.Menu;
import me.giverplay.minecraft2D.graphics.UI;

public class Services
{
	public static final int TICK = 0;
	public static final int RENDER = 1;
	
	private List<Entity> entities;
	private List<FutureRender> smoothRenders;
	
	private Game game;
	private Menu menu;
	private UI ui;
	
	private BufferedImage image;
	
	private boolean showGameOver = true;
	
	private int gameOverFrames = 0;
	private int maxGameOverFrames = 30;
	
	public Services(Game game)
	{
		this.game = game;
		
		entities = new ArrayList<>();
		smoothRenders = new ArrayList<>();
		
		ui = new UI();
		menu = new Menu(game);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
	}
	
	public void tick()
	{
		game.getListeners().tick();
		menu.tick();
		
		switch (game.getState())
		{
			case NORMAL:
			case INVENTORY:
			case CRAFT:
				
				for(int i = 0; i < entities.size(); i++) entities.get(i).tick();
				
				break;
			
			case PAUSED:
				break;
				
			case GAME_OVER:
				
				if(game.getListeners().select.down)
					Game.handleRestart();
				
				break;
				
			default:
				break;
		}
	}
	
	public void render()
	{
		BufferStrategy bs = game.getWindow().getBufferStrategy();
		
		if(bs == null)
		{
			game.getWindow().createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(110, 200, 255));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		/** Renderiaza��o do Jogo **/
		
		game.getWorld().render(g);
		
		Collections.sort(entities, Entity.sortDepth);
		
		for(int i = 0; i < entities.size(); i++) entities.get(i).render(g);
		
		/******/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		renderSmooth(g);
		
		switch (game.getState())
		{
			case GAME_OVER:

				Graphics2D g2 = (Graphics2D) g;
				
				g2.setColor(new Color(0, 0, 0, 100));
				g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
				
				String txt = "Game Over";
				g.setColor(Color.WHITE);
				g.setFont(FontUtils.getFont(32, Font.BOLD));
				g.drawString(txt, (WIDTH * SCALE - g.getFontMetrics(g.getFont()).stringWidth(txt)) / 2, HEIGHT * SCALE / 2);
				
				gameOverFrames++;
				
				if(gameOverFrames > maxGameOverFrames)
				{
					gameOverFrames = 0;
					showGameOver = !showGameOver;
				}
				
				if(showGameOver)
				{
					g.setFont(FontUtils.getFont(24, Font.BOLD));
					g.drawString("> Aperte ENTER para reiniciar <", (WIDTH * SCALE - g.getFontMetrics(g.getFont()).stringWidth("> Aperte ENTER para reiniciar <")) / 2, HEIGHT * SCALE / 2 + 28);
				}
			
				break;
			
			case PAUSED:
				
				menu.render(g);
				
				break;
				
			default:
				break;
		}
		
		bs.show();
	
	}
	
	public void renderSmooth(Graphics g)
	{
		for(int i = 0; i < smoothRenders.size(); i++)
		{
			FutureRender run = smoothRenders.get(i);
			run.render(g);
			smoothRenders.remove(run);
			
		}
		
		ui.render(g);
		
		g.setColor(new Color(100, 100, 100));
		g.setFont(FontUtils.getFont(18, Font.PLAIN));
		
		// FPS
		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(11, Font.PLAIN));
		g.drawString("FPS: " + Game.FPS, 0, Game.HEIGHT * Game.SCALE - 2);
	}
	
	public List<FutureRender> getSmoothRenders()
	{
		return this.smoothRenders;
	}
	
	public List<Entity> getEntities()
	{
		return entities;
	}
	
	public void restart()
	{
		entities.clear();
		
		Player pl = new Player(50, 170 * 16, 16, 16);
		entities.add(pl);
		SaveWrapper data = new SaveWrapper("Save", pl, game.getWorld(), entities);
		game.load(data);
		game.setState(State.NORMAL);
	}

	public UI getUI()
	{
		return this.ui;
	}
	
	public void setEntities(List<Entity> entities)
	{
		this.entities = entities;
	}
	
	public Menu getMenu()
	{
		return this.menu;
	}
}
