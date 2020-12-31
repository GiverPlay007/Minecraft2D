package me.giverplay.minecraft2D.game;

public enum GameMode
{
	SURVIVAL("Survival"),
	CREATIVE("Creative");

	private final String descName;

	GameMode(String name)
	{
		this.descName = name;
	}

	public String getDescriptionName()
	{
		return descName;
	}

	public static GameMode parse(String name)
	{
		try {
			return valueOf(name);
		} catch(IllegalArgumentException e) {
			return SURVIVAL;
		}
	}
}
