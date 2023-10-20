package me.giverplay.minecraft2D.entity;

import me.giverplay.minecraft2D.algorithms.Node;
import me.giverplay.minecraft2D.algorithms.Vector2i;
import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.world.Tile;

import java.util.List;

public abstract class EntityMob extends EntityLiving {

  protected static final int DIR_RIGHT = 0;
  protected static final int DIR_LEFT = 1;

  protected List<Node> path;

  protected double speed;

  protected boolean movingRight;
  protected boolean movingLeft;
  protected boolean jumping;
  protected boolean jump;
  protected boolean moving;
  protected boolean fallen;

  protected int maxFallingFrames = 20;
  protected int fallingFrames = 0;
  protected int direction;

  protected double fallingCoefficient = 0.24;
  protected double take = 0D;

  protected double lastX;
  protected double lastY;

  protected double gravity = 0.4;
  protected double vspd = 0;

  public EntityMob(Game game, double x, double y, int width, int height, int life, int maxLife, double speed) {
    super(game, x, y, width, height, life, maxLife);
    this.speed = speed;
  }

  public EntityMob(Game game, double x, double y, int width, int height, int maxLife, double speed) {
    this(game, x, y, width, height, maxLife, maxLife, speed);
  }

  @Override
  public void tick() {
    tickGravity();

    if(movingRight && isMoveAllowed((int) (x + speed), getY()))
      moveX(speed);

    if(movingLeft && isMoveAllowed((int) (x - speed), getY()))
      moveX(-speed);

    direction = lastX > x ? DIR_LEFT : DIR_RIGHT;
    moving = movingRight || movingLeft;

    lastX = x;
    lastY = y;
  }

  private void tickGravity() {
    vspd += gravity;

    if(jump) {
      jump = false;

      if(!isMoveAllowed(getX(), getY() + 1) && isMoveAllowed(getX(), getY() - 1)) {
        vspd = -4;
      }
    }

    if(!isMoveAllowed(getX(), (int) (y + vspd))) {
      int signVsp;

      if(vspd >= 0) {
        signVsp = 1;
      } else {
        signVsp = -1;
      }

      while (isMoveAllowed(getX(), (int) (y + signVsp))) {
        y = y + signVsp;
      }

      vspd = 0;
    }

    if(vspd > 0) {
      fallingFrames++;

      if(fallingFrames >= maxFallingFrames) {
        fallen = true;
        take = (vspd * fallingCoefficient);
      }
    } else {
      fallingFrames = 0;

      if(fallen) {
        fallen = false;
        damage((int) take);
      }
    }

    y = y + vspd;
  }

  public void followPath(List<Node> path) {
    if(path != null) {
      if(path.size() > 0) {
        Vector2i target = path.get(path.size() - 1).tile;

        if(x < target.x * Tile.SIZE) {
          x++;
        } else if(x > target.x * Tile.SIZE) {
          x--;
        }

        if(y < target.y * Tile.SIZE) {
          y++;
        } else if(y > target.y * Tile.SIZE) {
          y--;
        }

        if(x == target.x * Tile.SIZE && y == target.y * Tile.SIZE)
          path.remove(path.size() - 1);
      }
    }
  }

  public boolean isMoveAllowed(int nextX, int nextY) {
    return getWorld().moveAllowed(nextX + getMaskX(), nextY + getMaskY(), getWidth() - getMaskWidth(), getHeight() - getMaskHeight());
  }

  public void moveX(double d) {
    x += d;
  }

  public void moveY(double d) {
    y += d;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public boolean isMovingRight() {
    return movingRight;
  }

  public void setMovingRight(boolean movingRight) {
    this.movingRight = movingRight;
  }

  public boolean isMovingLeft() {
    return movingLeft;
  }

  public void setMovingLeft(boolean movingLeft) {
    this.movingLeft = movingLeft;
  }

  public boolean isJumping() {
    return jumping;
  }

  public void jump() {
    jump = true;
  }
}
