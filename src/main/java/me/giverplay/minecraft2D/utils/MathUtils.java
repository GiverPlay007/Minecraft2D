package me.giverplay.minecraft2D.utils;

public class MathUtils
{
  public static double pointDistance(int x1, int y1, int x2, int y2)
  {
    return Math.sqrt((x2 - x1) * (x2 - x1) + ((y2 - y1) * (y2 - y1)));
  }

  public static double calcFallDamage(int time, double coefficient)
  {
    return (coefficient * ((time / 60) ^ 1) * 5);
  }
}
