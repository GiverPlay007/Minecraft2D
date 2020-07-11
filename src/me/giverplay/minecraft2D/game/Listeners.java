package me.giverplay.minecraft2D.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.inventory.PlayerInventory;

public class Listeners implements KeyListener, MouseListener, MouseWheelListener
{
	private Game game;
	
	public Listeners(Game game)
	{
		this.game = game;
		
		game.getWindow().addKeyListener(this);
		game.getWindow().addMouseWheelListener(this);
		game.getWindow().addMouseListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(game.getState() != State.GAME_OVER)
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
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		((PlayerInventory) game.getPlayer().getInventory()).handleClick(e.getX() / Game.SCALE, e.getY() / Game.SCALE, e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		((PlayerInventory) game.getPlayer().getInventory()).updateFocus(e.getWheelRotation());
	}
}
