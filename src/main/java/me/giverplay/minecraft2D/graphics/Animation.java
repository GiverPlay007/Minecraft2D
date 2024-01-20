package me.giverplay.minecraft2D.graphics;

import java.awt.image.BufferedImage;

public class Animation {

  private final BufferedImage[] frames;
  private final long period;

  private long lastFrame = System.currentTimeMillis();
  private int index;

  public Animation(BufferedImage[] frames, long period) {
    this.frames = frames;
    this.period = period;
  }

  public BufferedImage getFrame() {
    return frames[index];
  }

  public void nextFrame() {
    if(System.currentTimeMillis() - lastFrame >= period) {
      lastFrame = System.currentTimeMillis();

      index++;

      if(index >= frames.length) {
        index = 0;
      }
    }
  }
}
