package me.giverplay.minecraft2D.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class GameSave
{
	private final File saveFolder;
	private final File worldsFolder;
	private final Game game;

	protected GameSave(Game game)
	{
		this.saveFolder = new File(System.getProperty("user.home") + File.separator + ".craftzinho");
		this.worldsFolder = new File(saveFolder, "worlds");
		this.game = game;

		if(!worldsFolder.exists())
			worldsFolder.mkdirs();
	}

	protected void saveGame(GameData data) throws Throwable
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(saveFolder, data.getName() + ".dat")));
		writer.write(data.serialize());
		writer.flush();
		writer.close();
	}

	protected void loadGame(String worldName) throws Throwable
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(saveFolder, worldName + ".dat")));

		StringBuilder saveString = new StringBuilder();
		String line;

		while((line = reader.readLine()) != null)
		{
			saveString.append(line);
		}

		reader.close();

		GameData data = new GameData(game);
		data.deserializeAndApply(saveString.toString());
	}

	protected File getWorldsFolder()
	{
		return this.worldsFolder;
	}

	protected File getSaveFolder()
	{
		return this.saveFolder;
	}
}
