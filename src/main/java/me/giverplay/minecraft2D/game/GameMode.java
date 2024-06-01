package me.giverplay.minecraft2D.game;

public enum GameMode {
  SURVIVAL("Survival"), CREATIVE("Creative");

  private final String descName;

  GameMode(String name) {
    this.descName = name;
  }

  public String getDescriptionName() {
    return descName;
  }

  public static GameMode parse(String name) {
    try {
      return parseByName(name);
    } catch (IllegalArgumentException e) {
      return parseByOrdinal(name);
    }
  }

  public static boolean isGameMode(String name) {
    try {
      parse(name.toUpperCase());
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private static GameMode parseByName(String name) {
    return valueOf(name.toUpperCase());
  }

  private static GameMode parseByOrdinal(String name) {
    int ordinal;

    try {
      ordinal = Integer.parseInt(name);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Not a number");
    }

    return parseByOrdinal(ordinal);
  }

  private static GameMode parseByOrdinal(int order) {
    GameMode[] values = values();

    for (int i = 0; i < values.length; i++) {
      if(values[i].ordinal() == order) return values[i];
    }

    throw new IllegalArgumentException("Unknown gamemode");
  }
}
