package me.giverplay.minecraft2D.graphics;

import static me.giverplay.minecraft2D.Game.HEIGHT;
import static me.giverplay.minecraft2D.Game.SCALE;
import static me.giverplay.minecraft2D.Game.WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.game.SaveWrapper;
import me.giverplay.minecraft2D.game.State;

public class Menu
{
	private static final int xButton = (WIDTH * SCALE) / 2;
	private static final int yButton = (HEIGHT * SCALE) / 2 - 100;
	private static final int dw = 256;
	private static final int dh = 44;
	
	private List<Button> buttons;
	
	private Game game;
	
	private Button focusedButton = null;
	
	private Button start;
	private Button exit;
	private Button save;
	private Button load;
	
	private boolean press = false;
	
	private int x = 0;
	private int y = 0;
	
	public Menu(Game game)
	{
		this.game = game;
		
		start = new Button(this, "Novo Jogo", xButton - dw / 2, yButton, dw, dh);
		exit = new Button(this, "Sair", xButton - dw / 2, yButton + dh * 2 + 10, dw, dh);
		
		save = new Button(this, "Salvar", xButton - dw / 2, yButton + dh + 5, dw / 2 - 5, dh);
		load = new Button(this, "Carregar", xButton + 5, yButton + dh + 5, dw / 2 - 5, dh);
		
		exit.setClickHandler(() -> {
			
			Game.rich.shutdown();
			System.out.println("Saindo");
			System.exit(0);
			
		});
		
		start.setClickHandler(() -> {
			
			Game.rich.update("Em jogo", "Sobrevivendo");
			game.setState(State.NORMAL);
			start.setText("Continuar");
			
		});
		
		save.setClickHandler(() -> {
			
			game.save();
			game.getUI().addToast(new Toast("Jogo salvo", 10, 10));
			
		});
		
		load.setClickHandler(() -> {
			
			if(SaveWrapper.canLoad())
			{
				game.handleLoad();
				game.setState(State.NORMAL);
				game.getUI().addToast(new Toast("Jogo Carregado", 10, 10));
			}
				
		});
		
		buttons = Arrays.asList(start, exit, save, load);
	}
	
	public void tick()
	{
		if(game.getListeners().menu.down)
			game.setState(State.PAUSED);
			
		if(press)
		{
			press = false;
			
			if(focusedButton != null)
				focusedButton.click();
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(0, 0, 0, 128));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).render(g);
		}
	}
	
	public void updateLoc(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < buttons.size(); i++)
		{
			Button but = buttons.get(i);
			
			if(x >= but.getY() && y >= but.getY() && y <= but.getY() + but.getHeight() && x <= but.getX() + but.getWidth())
			{
				focusedButton = but;
				return;
			}
		}
		
		focusedButton = null;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public void click()
	{
		press = true;
	}
	
	public Button getFocusedButton()
	{
		return this.focusedButton;
	}
}