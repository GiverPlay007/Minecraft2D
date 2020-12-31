package me.giverplay.minecraft2D.game;

import me.giverplay.minecraft2D.Main;
import me.giverplay.minecraft2D.command.CommandManager;
import me.giverplay.minecraft2D.command.CommandTask;
import me.giverplay.minecraft2D.entity.Entity;
import me.giverplay.minecraft2D.entity.entities.PlayerEntity;
import me.giverplay.minecraft2D.graphics.gui.Menu;
import me.giverplay.minecraft2D.graphics.gui.UI;
import me.giverplay.minecraft2D.sound.Sound;
import me.giverplay.minecraft2D.utils.FontUtils;
import me.giverplay.minecraft2D.utils.ThreadUtils;
import me.giverplay.minecraft2D.world.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Game
{
	public static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 512;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
			
	protected final ArrayList<Entity> entities = new ArrayList<>();

	protected PlayerEntity player;
	protected World world;

	private CommandManager commandManager;
	private CommandTask cmdTask;
	private GameInput gameInput;
	private State state = State.NORMAL;
	private GameTask gameTask;
	private Window window;
	private Camera camera;
	private Menu menu;
	private UI ui;

	private BufferedImage layer;

	private boolean showGameOver = true;

	private int gameOverFrames = 0;
	private final int maxGameOverFrames = 30;

	private volatile boolean isRunning = false;

	public Game()
	{
		initFrame();
		initGame();
	}

	private void initFrame()
	{
		window = new Window(this);
		gameInput = new GameInput(this);
	}
	
	private void initGame()
	{
		commandManager = new CommandManager();
		menu = new Menu(this);
		ui = new UI(this);

		layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		camera = new Camera(0, 0);

		world = new World(this, 250, 250, 53.4331);
		player = new PlayerEntity(this, 150, 170);
		entities.add(player);

		setState(State.PAUSED);
	}

	public void restart()
	{
		if(isRunning)
			stop();

		entities.clear();
		player = new PlayerEntity(this, 50, 170 * 16);
		entities.add(player);
		world = new World(this, 240, 240, 0.293);

		setState(State.NORMAL);
		start();
	}

	public synchronized void start()
	{
		isRunning = true;

		gameTask = new GameTask(this);
		gameTask.start();

		cmdTask = new CommandTask(this);
		cmdTask.start();

		Main.discordRichPresence.update("No menu", "");
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		ThreadUtils.join(gameTask);
		ThreadUtils.join(cmdTask);
	}

	public void load(GameData data)
	{
		this.player = data.getPlayer();
		this.player.updateCamera();

		this.world = data.getWorld();
		this.entities.clear();
		this.entities.addAll(data.getEntities());
	}

	public void loadSave()
	{
		try {
			load(GameSave.loadGame(this, "Save"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save()
	{
		GameData data = new GameData("Save", getPlayer(), getWorld(), new ArrayList<>(entities));

		try {
			GameSave.saveGame(data);
		} catch(IOException e) {
			e.printStackTrace();
		}

		setState(State.NORMAL);
	}

	public void tick()
	{
		gameInput.tick();
		menu.tick();

		switch (getState())
		{
			case NORMAL:
			case INVENTORY:
			case CRAFT:

				for(int i = 0; i < entities.size(); i++) entities.get(i).tick();

				break;

			case PAUSED:
				break;

			case GAME_OVER:

				if(gameInput.select.down)
					restart();

				break;

			default:
				break;
		}
	}

	public void render()
	{
		Graphics g = layer.getGraphics();

		g.setColor(new Color(110, 200, 255));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		getWorld().render(g);

		entities.sort(Entity.depthSorter);
		entities.forEach(entity -> entity.render(g));

		Graphics graphics = window.getRenderGraphics();
		graphics.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		renderNoScale(graphics);

		switch (getState())
		{
			case GAME_OVER:
				graphics.setColor(new Color(0, 0, 0, 100));
				graphics.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

				String txt = "Game Over";
				graphics.setColor(Color.WHITE);
				graphics.setFont(FontUtils.getFont(32, Font.BOLD));
				graphics.drawString(txt, (WIDTH * SCALE - graphics.getFontMetrics().stringWidth(txt)) / 2, HEIGHT * SCALE / 2);

				gameOverFrames++;

				if(gameOverFrames > maxGameOverFrames)
				{
					gameOverFrames = 0;
					showGameOver = !showGameOver;
				}

				if(showGameOver)
				{
					graphics.setFont(FontUtils.getFont(24, Font.BOLD));
					graphics.drawString("> Aperte ENTER para reiniciar <", (WIDTH * SCALE - graphics.getFontMetrics().stringWidth("> Aperte ENTER para reiniciar <")) / 2, HEIGHT * SCALE / 2 + 28);
				}

				break;

			case PAUSED:
				menu.render(graphics);
				break;

			default:
				break;
		}

		window.getBufferStrategy().show();
	}

	public void renderNoScale(Graphics g)
	{
		ui.render(g);

		g.setColor(new Color(100, 100, 100));
		g.setFont(FontUtils.getFont(18, Font.PLAIN));

		g.setColor(Color.WHITE);
		g.setFont(FontUtils.getFont(11, Font.PLAIN));
		g.drawString("TPS: " + gameTask.getTps(), 0, Game.HEIGHT * Game.SCALE - 14);
		g.drawString("FPS: " + gameTask.getFps(), 0, Game.HEIGHT * Game.SCALE - 2);
	}

	public void doGameOver()
	{
		setState(State.GAME_OVER);
		Sound.lose.play();
	}

	public void removeEntity(Entity entity)
	{
		entities.remove(entity);
	}

	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}

	public PlayerEntity getPlayer()
	{
		return this.player;
	}

	public World getWorld()
	{
		return this.world;
	}

	public State getState()
	{
		return state;
	}

	public Camera getCamera()
	{
		return this.camera;
	}

	public Window getWindow()
	{
		return this.window;
	}

	public UI getUI()
	{
		return this.ui;
	}

	public Menu getMenu()
	{
		return this.menu;
	}

	public GameInput getInput()
	{
		return gameInput;
	}

	public CommandManager getCommandManager()
	{
		return this.commandManager;
	}

	public synchronized boolean isRunning()
	{
		return this.isRunning;
	}

	public void setState(State state)
	{
		this.state = state;
	}
}
