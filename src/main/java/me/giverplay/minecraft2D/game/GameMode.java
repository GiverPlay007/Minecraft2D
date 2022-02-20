package me.giverplay.minecraft2D.game;

public enum GameMode {
  SURVIVAL("Survival"),
  CREATIVE("Creative");

  private final String descName;

  GameMode(String name) {
    this.descName = name;
  }

  public String getDescriptionName() {
    return descName;
  }

  public static GameMode parse(String name) // TODO: Parse by desc name and number
  {
    try {
      return valueOf(name.toUpperCase());
    } catch (IllegalArgumentException e) {
      return SURVIVAL;
    }
  }

  public static boolean isGameMode(String name) // TODO: New check
  {
    try {
      valueOf(name.toUpperCase());
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
