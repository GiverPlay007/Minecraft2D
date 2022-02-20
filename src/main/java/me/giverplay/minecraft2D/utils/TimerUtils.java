package me.giverplay.minecraft2D.utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils {

  public static void runLater(int seconds, TimerTask task) {
    new Timer().schedule(task, seconds * 1000);
  }

  public static void runTaskTimer(int seconds, Runnable task) {
    new javax.swing.Timer(seconds * 1000, e -> task.run()).start();
  }
}