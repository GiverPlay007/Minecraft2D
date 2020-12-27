package me.giverplay.minecraft2D.graphics;

public class Toast
{
	private String text;
	
	private int fadeIn;
	private int fadeOut;
	
	public Toast(String text, int fadeIn, int fadeOut)
	{
		this.text = text;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
	}
	
	public void addLine(String line)
	{
		text += System.lineSeparator();
		text += line;
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getFadeIn()
	{
		return fadeIn;
	}
	
	public int getFadeOut()
	{
		return this.fadeOut;
	}
}
