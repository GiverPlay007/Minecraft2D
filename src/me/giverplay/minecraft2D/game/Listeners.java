package me.giverplay.minecraft2D.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.giverplay.minecraft2D.Game;
import me.giverplay.minecraft2D.inventory.PlayerInventory;

public class Listeners implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
{
	private Game game;
	
	public Listeners(Game game)
	{
		this.game = game;
		
		game.getWindow().addKeyListener(this);
		game.getWindow().addMouseWheelListener(this);
		game.getWindow().addMouseListener(this);
		game.getWindow().addMouseMotionListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(game.getState() == State.NORMAL)
		{
			updateSlot(event);
		}
		
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			if(game.getState() == State.NORMAL)
			{
				game.setState(State.PAUSED);
				Game.rich.update("No Menu", "");
			}
		}
		
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
		switch (game.getState())
		{
			case NORMAL:
				((PlayerInventory) game.getPlayer().getInventory()).handleClick(e.getX() / Game.SCALE, e.getY() / Game.SCALE, e.getButton());
				break;
				
			case PAUSED:
				game.getMenu().click();
				break;
				
			default:
				break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(game.getState() == State.NORMAL)
			((PlayerInventory) game.getPlayer().getInventory()).updateFocus(e.getWheelRotation());
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		game.getMenu().updateLoc(e.getX(), e.getY());
	}
	
	private void updateSlot(KeyEvent event)
	{
		PlayerInventory inv = (PlayerInventory) game.getPlayer().getInventory();
		
		switch (event.getKeyCode())
		{
			case KeyEvent.VK_1:
				inv.setFocus(0);
				break;
			
			case KeyEvent.VK_2:
				inv.setFocus(1);
				break;
				
			case KeyEvent.VK_3:
				inv.setFocus(2);
				break;
				
			case KeyEvent.VK_4:
				inv.setFocus(3);
				break;
				
			case KeyEvent.VK_5:
				inv.setFocus(4);
				break;
				
			case KeyEvent.VK_6:
				inv.setFocus(5);
				break;
				
			case KeyEvent.VK_7:
				inv.setFocus(6);
				break;
				
			case KeyEvent.VK_8:
				inv.setFocus(7);
				break;
				
			case KeyEvent.VK_9:
				inv.setFocus(8);
				break;
				
			default:
				break;
		}
	}
}
