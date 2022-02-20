package me.giverplay.minecraft2D.graphics.gui;

import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.utils.FontUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {

  private final Menu menu;

  private final int width;
  private final int height;
  private final int x;
  private final int y;

  private Runnable handler;
  private String text;

  public Button(Menu menu, String text, int x, int y, int width, int height) {
    this.menu = menu;
    this.text = text;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  public void render(Graphics g) {
    g.drawImage(Sprites.ICON_BUTTON, x, y, width, height, null);
    g.drawRect(x, y, width, height);

    if(menu.getFocusedButton() == this)
      g.drawImage(Sprites.ICON_SEL, x, y, width, height, null);

    g.setFont(FontUtils.getFont(20, Font.BOLD));
    g.setColor(Color.BLACK);
    g.drawString(getText(), x + (width - FontUtils.stringWidth(g, text)) / 2, y + height / 2 + 8);
  }

  public void setClickHandler(Runnable run) {
    this.handler = run;
  }

  public void click() {
    if(handler != null) {
      handler.run();
    }
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
