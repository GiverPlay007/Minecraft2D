package me.giverplay.minecraft2D.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import me.giverplay.minecraft2D.inventory.PlayerInventory;

public class GameInput implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
{
	public List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();
	public Key inventory = new Key();
	public Key interact = new Key();
	public Key jump = new Key();
	public Key select = new Key();
	
	private final Game game;
	
	public GameInput(Game game)
	{
		this.game = game;
		game.getWindow().addKeyListener(this);
		game.getWindow().addMouseWheelListener(this);
		game.getWindow().addMouseListener(this);
		game.getWindow().addMouseMotionListener(this);
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
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(game.getState() == State.NORMAL)
			((PlayerInventory) game.getPlayer().getInventory()).updateFocus(e.getWheelRotation());
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		game.getMenu().updateLoc(e.getX(), e.getY());
	}
	
	private void updateSlot(KeyEvent event)
	{
		if(game.getState() != State.NORMAL)
			return;
		
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
	
	public void releaseAll()
	{
		for (int i = 0; i < keys.size(); i++)
		{
			keys.get(i).down = false;
		}
	}
	
	public void tick()
	{
		keys.forEach(Key::tick);
	}
	
	@Override
	public void keyPressed(KeyEvent ke)
	{
		toggle(ke, true);
		updateSlot(ke);
	}
	
	@Override
	public void keyReleased(KeyEvent ke)
	{
		toggle(ke, false);
	}
	
	private void toggle(KeyEvent ke, boolean pressed)
	{
		if (ke.getKeyCode() == KeyEvent.VK_W || ke.getKeyCode() == KeyEvent.VK_UP)
			up.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_S || ke.getKeyCode() == KeyEvent.VK_DOWN)
			down.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_A || ke.getKeyCode() == KeyEvent.VK_LEFT)
			left.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_D || ke.getKeyCode() == KeyEvent.VK_RIGHT)
			right.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
			attack.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
			menu.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_E || ke.getKeyCode() == KeyEvent.VK_TAB)
			inventory.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_F)
			interact.toggle(pressed);
		
		if(ke.getKeyCode() == KeyEvent.VK_SPACE)
			jump.toggle(pressed);
		
		if(ke.getKeyCode() == KeyEvent.VK_ENTER)
			select.toggle(pressed);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO
	}
	
	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO
	}
	
	public class Key
	{
		public int presses, absorbs;
		public boolean down, clicked;
		
		public Key()
		{
			keys.add(this);
		}
		
		public void toggle(boolean pressed)
		{
			if (pressed != down)
			{
				down = pressed;
			}
			if (pressed)
			{
				presses++;
			}
		}
		
		public void tick()
		{
			if (absorbs < presses)
			{
				absorbs++;
				clicked = true;
			} else
			{
				clicked = false;
			}
		}
	}
}
