package me.giverplay.minecraft2D;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import me.giverplay.minecraft2D.command.CommandManager;
import me.giverplay.minecraft2D.command.CommandTask;
import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.GameTask;
import me.giverplay.minecraft2D.game.Listeners;
import me.giverplay.minecraft2D.game.Services;
import me.giverplay.minecraft2D.game.State;
import me.giverplay.minecraft2D.graphics.FutureRender;
import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.sound.Sound;
import me.giverplay.minecraft2D.world.World;

public class Game extends Canvas
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static int FPS = 0;
	
	private static Game game;
			
	private State state = State.PAUSED;
	private GameTask thread;
	private Services services;
	private CommandTask cmdTask;
	private CommandManager commandManager;
	private Camera camera;
	private Spritesheet sprite;
	private World world;
	private Player player;
	
	private JFrame frame;
	
	private boolean isRunningRelative = false;
	private boolean isRunning = false;
	
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
		services = new Services(this);
		
		camera = new Camera(0, 0);
		sprite = new Spritesheet("/Spritesheet.png");
		player = new Player(50, 190 * 16, 16, 16);
		world = new World(200, 200);
		
		services.getEntities().add(player);
		
		setState(State.NORMAL);
	}
	
	// Metodos de Controle do Fluxo | TODO
	
	public synchronized void start()
	{
		isRunning = true;
		thread = new GameTask(this);
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
		services.restart();
	}
	
	public static void handleRestart()
	{
		game.restart();
	}
	
	public synchronized void runService(int s)
	{
		if(s == Services.TICK)
		{
			services.tick();
		}
		else if(s == Services.RENDER)
		{
			services.render();
		}
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
		return services.getEntities();
	}

	public void matar()
	{
		setState(State.GAME_OVER);
		Sound.lose.play();
	}
	
	public Camera getCamera()
	{
		return this.camera;
	}
	
	public void addSmoothRender(FutureRender run)
	{
		services.getSmoothRenders().add(run);
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
	
	public boolean isRunningThread()
	{
		return this.isRunning;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}
}
