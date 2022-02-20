package me.giverplay.minecraft2D.game;

public class Camera {

  private int x;
  private int y;

  public Camera(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  public int getX() {
    return this.x;
  }

  public static int clamp(int current, int min, int max) {
    if(current < min)
      current = min;

    if(current > max)
      current = max;

    return current;
  }
}
