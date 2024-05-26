package me.giverplay.minecraft2D.graphics.gui;

import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.game.GameMode;
import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.Item;
import me.giverplay.minecraft2D.utils.FontUtils;
import me.giverplay.minecraft2D.utils.GraphicsUtils;
import me.giverplay.minecraft2D.world.Material;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import static me.giverplay.minecraft2D.game.Game.HEIGHT;
import static me.giverplay.minecraft2D.game.Game.SCALE;
import static me.giverplay.minecraft2D.game.Game.WIDTH;

public class UI {

  private final ArrayList<Toast> toasts = new ArrayList<>();

  private final Game game;
  private Toast toast;

  private boolean showingToast = false;

  private int toastFadeIn = 0;
  private int toastFadeOut = 0;
  private int toastFrames = 0;
  private int maxToastFrames = 0;

  public UI(Game game) {
    this.game = game;
  }

  public void render(Graphics g) {
    g.setColor(Color.YELLOW);
    g.setFont(FontUtils.getFont(16, Font.PLAIN));

    renderHearth(g);
    renderInventory(g, game.getPlayer().getInventory());
    advanceToast(g);
  }

  private void renderHearth(Graphics g) {
    if(game.getPlayer().getGameMode() != GameMode.SURVIVAL)
      return;

    int max = game.getPlayer().getMaxLife();
    int cur = game.getPlayer().getLife();

    int heartSize = 16;

    for (int i = 0; i < max; i++) {
      g.drawImage(i < cur ? Sprites.ICON_LIFE_FULL : Sprites.ICON_LIFE_NON_FULL, i * (heartSize + 5), 0, heartSize, heartSize, null);
    }
  }

  private void renderInventory(Graphics g, Inventory inv) {
    g.setColor(new Color(0, 0, 0, 144));

    int slotSize = 32;
    int xs = (WIDTH * SCALE) / 2 - 9 * slotSize / SCALE;
    int ys = (HEIGHT * SCALE) - slotSize - 5;

    g.fillRect(xs, ys, 9 * slotSize, slotSize);
    g.setFont(new Font("arial", Font.BOLD, 16));
    g.setColor(Color.WHITE);
    g.drawRect(xs + inv.getFocusedSlot() * slotSize + 1, ys + 1, slotSize - 2, slotSize - 2);

    for (int i = 0; i < 9; i++) {
      int n = xs + i * slotSize;
      g.setColor(Color.BLACK);
      g.drawRect(n, ys, slotSize, slotSize);
      g.setColor(Color.yellow);

      Item item = inv.getItem(i);

      if(item.getType() != Material.AIR) {
        g.drawImage(item.getSprite(), n + 4, ys + 4, 24, 24, null);

        if(item.getAmount() >= 2) {
          String txt = String.valueOf(item.getAmount());
          GraphicsUtils.drawOutline(g, txt, n + 30 - FontUtils.stringWidth(g, txt), ys + 30, 4f, Color.BLACK, Color.WHITE);
        }
      }
    }
  }

  public void advanceToast(Graphics g) {
    int time = 50;
    if(!showingToast) {
      if(toasts.isEmpty())
        return;

      toast = toasts.get(0);
      showingToast = true;
      toastFadeIn = toast.getFadeIn();
      toastFadeOut = toast.getFadeOut();
      maxToastFrames = toastFadeIn + toastFadeOut + time;
    }

    toastFrames++;

    if(toastFrames >= maxToastFrames) {
      toasts.remove(0);
      toast = null;
      showingToast = false;
      toastFrames = 0;

      return;
    }

    int alpha = 255;

    if(toastFrames <= toastFadeIn) {
      alpha = 255 * toastFrames / toastFadeIn;
    } else if(toastFrames >= toastFadeIn + time) {
      alpha = 255 * (toastFadeOut - (toastFrames - (toastFadeIn + time))) / toastFadeOut;
    }

    Color currentColor = new Color(255, 255, 255, alpha);
    g.setColor(currentColor);

    g.setFont(FontUtils.getFont(32, Font.BOLD));
    int width = FontUtils.stringWidth(g, toast.getText());

    g.drawString(toast.getText(), (WIDTH * SCALE - width) / 2, HEIGHT * SCALE / 2);
  }

  public void addToast(Toast toast) {
    toasts.add(toast);
  }
}
