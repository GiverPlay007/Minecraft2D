package me.giverplay.minecraft2D.utils;

public class ThreadUtils
{
  public static void sleep(long millis)
  {
    try {
      Thread.sleep(millis);
    } catch(InterruptedException ignore){ }
  }

  public static void join(Thread thread)
  {
    try {
      thread.join();
    } catch(InterruptedException ignore) { }
  }
}
