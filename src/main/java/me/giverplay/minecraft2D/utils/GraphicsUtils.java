package me.giverplay.minecraft2D.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GraphicsUtils
{
	public static BufferedImage rotate(BufferedImage img, double angle)
	{
		double rads = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
		int w = img.getWidth();
		int h = img.getHeight();
		int newWidth = (int) Math.floor(w * cos + h * sin);
		int newHeight = (int) Math.floor(h * cos + w * sin);
		
		BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotated.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate((newWidth - w) / 2, (newHeight - h) / 2);
		
		int x = w / 2;
		int y = h / 2;
		
		at.rotate(rads, x, y);
		g2d.setTransform(at);
		g2d.drawImage(img, 0, 0, null);
		g2d.setColor(Color.RED);
		g2d.dispose();
		
		return rotated;
	}
	
	public static void drawOutline(Graphics g, String txt, int x, int y, float stroke, Color shapeColor, Color stringColor)
	{
		Graphics2D g2d = (Graphics2D) g.create();
    AffineTransform transform = g2d.getTransform();
    transform.translate(x, y);
    g2d.transform(transform);
    g2d.setColor(shapeColor);
    Shape shape = new TextLayout(txt, g.getFont(), g2d.getFontRenderContext()).getOutline(null);
    g2d.setStroke(new BasicStroke(stroke));
    g2d.draw(shape);
    g2d.setColor(stringColor);
    g2d.fill(shape);
    g2d.dispose();
	}
}
