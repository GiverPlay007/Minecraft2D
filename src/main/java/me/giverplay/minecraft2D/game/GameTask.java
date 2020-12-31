package me.giverplay.minecraft2D.game;

import me.giverplay.minecraft2D.utils.ThreadUtils;

public class GameTask extends Thread implements Runnable
{
	private final Game game;

	private int fps;
	private int tps;

	public GameTask(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void run()
	{
		game.getWindow().requestFocus();

		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		long now;

		double nsPerTick = 1_000_000_000 / 60.0D;
		double unprocessed = 0.0D;

		int fps = 0;
		int tps = 0;

		while(game.isRunning())
		{
			now = System.nanoTime();
			unprocessed += (now -lastTime) / nsPerTick;
			lastTime = now;

			while(unprocessed >= 1)
			{
				game.tick();
				++tps;
				--unprocessed;
			}

			game.render();
			fps++;

			if(System.currentTimeMillis() - timer >= 1000)
			{
				this.tps = tps;
				this.fps = fps;
				tps = 0;
				fps = 0;
				timer += 1000;
			}

			ThreadUtils.sleep(5);
		}
	}

	public int getFps()
	{
		return fps;
	}

	public int getTps()
	{
		return tps;
	}
}
