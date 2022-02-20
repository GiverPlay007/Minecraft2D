package me.giverplay.minecraft2D.entity;

import me.giverplay.minecraft2D.game.Game;

public abstract class EntityDrop extends Entity {

  public EntityDrop(Game game, double x, double y, int width, int height) {
    super(game, x, y, width, height);
    setDepth(0);
  }

  public void collect() {
    game.removeEntity(this);
  }
}
