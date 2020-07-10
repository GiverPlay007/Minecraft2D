package me.giverplay.minecraft2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import me.giverplay.minecraft2D.command.CommandManager;
import me.giverplay.minecraft2D.command.CommandTask;
import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.events.Listeners;
import me.giverplay.minecraft2D.graphics.Camera;
import me.giverplay.minecraft2D.graphics.FontUtils;
import me.giverplay.minecraft2D.graphics.FutureRender;
import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.graphics.UI;
import me.giverplay.minecraft2D.sound.Sound;
import me.giverplay.minecraft2D.world.World;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private List<Entity> entities;
	private List<FutureRender> smoothRenders;
	
	private static Game game;
	private static int FPS = 0;
	
	private CommandTask cmdTask;
	private CommandManager commandManager;
	private Camera camera;
	private Spritesheet sprite;
	private World world;
	private Player player;
	private UI ui;
	
	private BufferedImage image;
	private Thread thread;
	private JFrame frame;
	
	private boolean isRunningRelative = false;
	private boolean isRunning = false;
	private boolean showGameOver = true;
	private boolean morreu = false;
	private boolean nextLevel = false;
	
	private int gameOverFrames = 0;
	private int maxGameOverFrames = 30;
	
	public static Game getGame()
	{
		return game;
	}
	
	// Métodos Startup | TODO
	public Game()
	{
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		setupFrame();
		setupAssets();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.start();
	}
	
	private void setupFrame()
	{
		frame = new JFrame("Game 06 - Minecraft 2D Clone");
		frame.add(this);
		frame.setResizable(false);
		frame.setUndecorated(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		new Listeners(this);
	}
	
	private void setupAssets()
	{
		isRunningRelative = true;
		
		game = this;
		cmdTask = new CommandTask(this);
		cmdTask.start();
		
		commandManager = new CommandManager();
		
		entities = new ArrayList<>();
		smoothRenders = new ArrayList<>();
		
		camera = new Camera(0, 0);
		sprite = new Spritesheet("/Spritesheet.png");
		player = new Player(50, 1, 16, 16);
		world = new World(200, 200);
		
		ui = new UI();
		
		entities.add(player);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		
		nextLevel = false;
		morreu = false;
	}
	
	// Metodos de Controle do Fluxo | TODO
	
	public synchronized void start()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void restart()
	{
		setupAssets();
	}
	
	public static void handleRestart()
	{
		game.restart();
	}
	
	// Core | TODO
	
	@Override
	public void run()
	{
		requestFocus();
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		double ticks = 60.0D;
		double ns = 1000000000 / ticks;
		double delta = 0.0D;
		
		int fps = 0;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1)
			{
				tick();
				render();
				
				delta--;
				fps++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				FPS = fps;
				fps = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
	
	public synchronized void tick()
	{
		if(!morreu && !nextLevel)
		{
			for(int i = 0; i < entities.size(); i++) entities.get(i).tick();
		}
	}
	
	public synchronized void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(110, 200, 255));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		/** Renderiaza��o do Jogo **/
		
		world.render(g);
		
		Collections.sort(entities, Entity.sortDepth);
		
		for(int i = 0; i < entities.size(); i++) entities.get(i).render(g);
		
		/******/
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		renderSmooth(g);
		
		if(morreu || nextLevel)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			
			String txt = morreu ? "Game Over" : "Você Venceu!";
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
		g.drawString("FPS: " + FPS, 2, 12);
	}
	
	// Getters e Setters | TODO
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public Spritesheet getSpritesheet()
	{
		return this.sprite;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public List<Entity> getEntities()
	{
		return this.entities;
	}
	
	public boolean morreu()
	{
		return this.morreu;
	}
	
	public boolean venceu()
	{
		return this.nextLevel;
	}

	public void matar()
	{
		this.morreu = true;
		Sound.lose.play();
	}
	
	public Camera getCamera()
	{
		return this.camera;
	}
	
	public void addSmoothRender(FutureRender run)
	{
		smoothRenders.add(run);
	}
	
	public CommandTask getCommandTask()
	{
		return this.cmdTask;
	}
	
	public CommandManager getCommandManager()
	{
		return this.commandManager;
	}
	
	public boolean isRunning()
	{
		return this.isRunningRelative;
	}
}
