package me.giverplay.minecraft2D.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.giverplay.minecraft2D.Game;

public class Listeners implements KeyListener
{
	private Game game;
	
	public Listeners(Game game)
	{
		this.game = game;
		this.game.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(!game.morreu() && !game.venceu())
		{
			if(event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_UP)
			{
				game.getPlayer().handleJump();
			}
			
			if(event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D)
			{
				game.getPlayer().setWalkingRight(true);
			} 
			
			if(event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A)
			{
				game.getPlayer().setWalkingLeft(true);
			}
		}
		else
		{
			if(event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				Game.handleRestart();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D)
		{
			game.getPlayer().setWalkingRight(false);
		} 
		
		if(event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A)
		{
			game.getPlayer().setWalkingLeft(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
}
