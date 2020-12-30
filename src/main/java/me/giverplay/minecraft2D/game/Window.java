package me.giverplay.minecraft2D.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import me.giverplay.minecraft2D.Game;

public class Window extends Canvas
{
	private static final long serialVersionUID = 1L;

	private final Graphics graphics;

	public Graphics getRenderGraphics()
	{
		return graphics;
	}

	public Window(Game game)
	{
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		JFrame frame = new JFrame("Game 06 - Minecraft 2D Clone");
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		createBufferStrategy(3);
		graphics = getBufferStrategy().getDrawGraphics();

		try {
			frame.setIconImage(ImageIO.read(Game.class.getResourceAsStream("/cara.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
