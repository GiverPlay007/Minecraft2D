package me.giverplay.minecraft2D;

import java.io.IOException;
import java.util.List;

import me.giverplay.minecraft2D.command.CommandManager;
import me.giverplay.minecraft2D.command.CommandTask;
import me.giverplay.minecraft2D.entities.Entity;
import me.giverplay.minecraft2D.entities.Player;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.GameData;
import me.giverplay.minecraft2D.game.GameTask;
import me.giverplay.minecraft2D.game.Listeners;
import me.giverplay.minecraft2D.game.SaveWrapper;
import me.giverplay.minecraft2D.game.Services;
import me.giverplay.minecraft2D.game.State;
import me.giverplay.minecraft2D.game.Window;
import me.giverplay.minecraft2D.graphics.FutureRender;
import me.giverplay.minecraft2D.graphics.Menu;
import me.giverplay.minecraft2D.graphics.Spritesheet;
import me.giverplay.minecraft2D.graphics.UI;
import me.giverplay.minecraft2D.sound.Sound;
import me.giverplay.minecraft2D.world.World;

public class Game
{
	public static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 512;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	
	//public static final int WIDTH = 1024;
	//public static final int HEIGHT = 720;
	//public static final int SCALE = 1;
	
	public static DiscordRP discordRichPresence;
	
	private static boolean allReady = false;
	
	public static int FPS = 0;
	
	private static Game game;
			
	private State state = State.NORMAL;
	private Window window;
	private GameTask thread;
	private Services services;
	private CommandTask cmdTask;
	private CommandManager commandManager;
	private Camera camera;
	private Spritesheet sprite;
	private World world;
	private Player player;
	private Listeners listeners;
	
	private boolean isRunningRelative = false;
	private boolean isRunning = false;
	
	public static Game getGame()
	{
		return game;
	}
	
	public Game()
	{
		setupFrame();
		setupAssets();
	}
	
	public static void main(String[] args)
	{
		discordRichPresence = new DiscordRP();
		discordRichPresence.start();
		
		Sound.init();
		
		Game game = new Game();
		game.start();
	}
	
	private void setupFrame()
	{
		window = new Window(this);
		listeners = new Listeners(this);
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
		player = new Player(50, 16, 16, 16);
		world = new World(240, 240, 0.293);
		
		services.getEntities().add(player);
		
		setState(State.PAUSED);
		allReady = true;
	}
	
	public synchronized void start()
	{
		isRunning = true;
		thread = new GameTask(this);
		thread.start();
		discordRichPresence.update("No menu", "");
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
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public void load(GameData data)
	{
		this.player = data.getPlayer();
		player.updateCamera();
		this.world = data.getWorld();
		services.setEntities(data.getEntities());
		allReady = true;
	}
	
	public void save()
	{
		GameData data = new GameData("Save", getPlayer(), getWorld(), getEntities());
		
		try
		{
			SaveWrapper.saveGame(data);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		setState(State.NORMAL);
	}
	
	public static boolean allReady()
	{
		return allReady;
	}
	
	public UI getUI()
	{
		return services.getUI();
	}
	
	public Menu getMenu()
	{
		return services.getMenu();
	}

	public void handleLoad()
	{
		try
		{
			load(SaveWrapper.loadGame("Save"));
		} catch (IOException e)
		{
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	public Listeners getListeners()
	{
		return listeners;
	}
}
