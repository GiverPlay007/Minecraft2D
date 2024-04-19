package me.giverplay.minecraft2D.graphics.gui;

import me.giverplay.minecraft2D.Main;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.game.State;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import static me.giverplay.minecraft2D.game.Game.HEIGHT;
import static me.giverplay.minecraft2D.game.Game.SCALE;
import static me.giverplay.minecraft2D.game.Game.WIDTH;

public class Menu {

  private static final int xButton = (WIDTH * SCALE) / 2;
  private static final int yButton = (HEIGHT * SCALE) / 2 - 100;
  private static final int dw = 256;
  private static final int dh = 44;

  private final List<Button> buttons;

  private final Game game;
  private final Button start;

  private Button focusedButton = null;

  private boolean press = false;
  private boolean savable = false;
  private boolean mouseMoved = false;

  private long lastInputCheck;

  private int x = 0;
  private int y = 0;

  public Menu(Game game) {
    this.game = game;

    start = new Button(this, "Novo Jogo", xButton - dw / 2, yButton, dw, dh);
    Button exit = new Button(this, "Sair", xButton - dw / 2, yButton + dh * 2 + 10, dw, dh);

    Button save = new Button(this, "Salvar", xButton - dw / 2, yButton + dh + 5, dw / 2 - 5, dh);
    Button load = new Button(this, "Carregar", xButton + 5, yButton + dh + 5, dw / 2 - 5, dh);

    exit.setClickHandler(() -> {

      Main.discordRichPresence.shutdown();
      System.out.println("Saindo");
      System.exit(0);

    });

    start.setClickHandler(() -> {

      savable = true;
      Main.discordRichPresence.update("Em jogo", "Sobrevivendo");
      game.setState(State.NORMAL);
      start.setText("Continuar");

    });

    save.setClickHandler(() -> {

      if(!savable)
        return;

      game.save();
      game.getUI().addToast(new Toast("Jogo salvo", 10, 10));

    });

    load.setClickHandler(() -> {

      if(true) // TODO: Create world screen
      {
        savable = true;
        game.loadSave("world"); // TODO: Get name from new world screen
        game.setState(State.NORMAL);
        game.getUI().addToast(new Toast("Jogo Carregado", 10, 10));
        start.setText("Continuar");
      }

    });

    buttons = Arrays.asList(start, exit, save, load);
  }

  public void tick() {
    if(mouseMoved) {
      mouseMoved = false;
      updateMouseLocation();
    }

    boolean checkMenuPressed = false;

    long now = System.currentTimeMillis();

    if(game.getInput().menu.down) {
      if(now - lastInputCheck > 500) {
        lastInputCheck = now;
        checkMenuPressed = true;
      }
    }

    if(checkMenuPressed) {
      if(game.getState() == State.PAUSED) {
        if(!savable) {
          start.setText("Continuar");
        }

        game.setState(State.NORMAL);
        return;
      }

      game.setState(State.PAUSED);
      Main.discordRichPresence.update("No menu", "");
    }

    if(press) {
      press = false;

      if(focusedButton != null)
        focusedButton.click();
    }
  }

  public void render(Graphics g) {
    g.setColor(new Color(0, 0, 0, 128));
    g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

    buttons.forEach(button -> button.render(g));
  }

  private void updateMouseLocation() {
    for (Button but : buttons) {
      if(x >= but.getY() && y >= but.getY() && y <= but.getY() + but.getHeight() && x <= but.getX() + but.getWidth()) {
        focusedButton = but;
        return;
      }
    }

    focusedButton = null;
  }

  public void onMouseMove(int x, int y) {
    mouseMoved = true;
    this.x = x;
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  public int getX() {
    return this.x;
  }

  public void click() {
    press = true;
  }

  public Button getFocusedButton() {
    return this.focusedButton;
  }
}