package me.giverplay.minecraft2D.entity.entities;

import me.giverplay.minecraft2D.entity.EntityMob;
import me.giverplay.minecraft2D.game.Camera;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.game.GameInput;
import me.giverplay.minecraft2D.game.GameMode;
import me.giverplay.minecraft2D.game.State;
import me.giverplay.minecraft2D.graphics.Animation;
import me.giverplay.minecraft2D.graphics.Sprites;
import me.giverplay.minecraft2D.inventory.Inventory;
import me.giverplay.minecraft2D.inventory.PlayerInventory;
import me.giverplay.minecraft2D.world.Tile;

import java.awt.Graphics;

public class PlayerEntity extends EntityMob {

  private final Animation animation;
  private final GameInput input;
  private final Camera camera;

  private Inventory inv;
  private GameMode mode;

  public PlayerEntity(Game game, int x, int y) {
    super(game, x, y, Tile.SIZE, Tile.SIZE, 10, 2);

    setGameMode(GameMode.SURVIVAL);

    animation = new Animation(Sprites.SPRITE_PLAYER_RIGHT, 300);
    camera = game.getCamera();
    input = game.getInput();
    inv = new PlayerInventory(this, 36);

    ((PlayerInventory) inv).resetDefaults();

    setDepth(2);

    maskX = 5;
    maskY = 2;
    maskWidth = 10;
    maskHeight = 2;
  }

  @Override
  public void tick() {
    movingRight = input.right.down;
    movingLeft = input.left.down;

    if(input.jump.down || input.up.down)
      jump();

    ((PlayerInventory) inv).handleClick();

    super.tick();
    updateCamera();

    if(getLife() <= 0) {
      game.doGameOver();
    }
  }

  public void updateCamera() {
    camera.setX(Camera.clamp(getX() - (Game.WIDTH / 2), 0, getWorld().getWidth() * Tile.SIZE - Game.WIDTH));
    camera.setY(Camera.clamp(getY() - (Game.HEIGHT / 2), 0, getWorld().getHeight() * Tile.SIZE - Game.HEIGHT));
  }

  @Override
  public void render(Graphics g) {
    if(moving && game.getState() == State.NORMAL) {
      animation.nextFrame();
    }

    g.drawImage(animation.getFrame(), getX() - camera.getX(), getY() - camera.getY(), getWidth(), getHeight(), null);
  }

  @Override
  public void damage(int damage) {
    if(getGameMode() != GameMode.CREATIVE) {
      super.damage(damage);
    }
  }

  public Inventory getInventory() {
    return this.inv;
  }

  public GameMode getGameMode() {
    return mode;
  }

  public void setGameMode(GameMode mode) {
    this.mode = mode;
  }

  public void setInventory(PlayerInventory inv) {
    this.inv = inv;
  }
}
