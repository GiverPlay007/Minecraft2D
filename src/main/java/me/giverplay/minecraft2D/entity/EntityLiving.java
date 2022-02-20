package me.giverplay.minecraft2D.entity;

import me.giverplay.minecraft2D.game.Game;
import me.giverplay.minecraft2D.sound.Sound;

public abstract class EntityLiving extends Entity {

  private int life;
  private int maxLife;

  public EntityLiving(Game game, double x, double y, int width, int height, int life, int maxLife) {
    super(game, x, y, width, height);

    this.life = life;
    this.maxLife = maxLife;
  }

  public EntityLiving(Game game, double x, double y, int width, int height, int maxLife) {
    this(game, x, y, width, height, maxLife, maxLife);
  }

  public void damage(int damage) {
    Sound.damage.play();
    life -= damage;
  }

  public void heal(int heal) {
    Sound.heal.play();
    life += heal;

    if(this.life > maxLife)
      this.life = maxLife;
  }

  public int getLife() {
    return life;
  }

  public void setLife(int life) {
    this.life = life;

    if(this.life > maxLife)
      this.life = maxLife;
  }

  public int getMaxLife() {
    return maxLife;
  }

  public void setMaxLife(int maxLife) {
    if(maxLife < 1) return;

    this.maxLife = maxLife;
  }
}
