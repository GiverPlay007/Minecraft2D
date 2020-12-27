package me.giverplay.minecraft2D.game;

import me.giverplay.minecraft2D.Game;

public class GameTask extends Thread implements Runnable
{
	private Game game;
	
	public GameTask(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void run()
	{
		game.getWindow().requestFocus();
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		double ticks = 60.0D;
		double ns = 1000000000 / ticks;
		double delta = 0.0D;
		
		int fps = 0;
		
		while(game.isRunningThread())
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1)
			{
				game.runService(Services.TICK);
				game.runService(Services.RENDER);
				
				delta--;
				fps++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				Game.FPS = fps;
				fps = 0;
				timer += 1000;
			}
		}
		
		game.stop();
	}
}
