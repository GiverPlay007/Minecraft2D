package me.giverplay.minecraft2D.game;

import java.io.File;

public class SaveWrapper
{
	private static File dataFolder;
	private static File saveFolder;
	
	static
	{
		dataFolder = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.outroMinecraft");
		
		if(!dataFolder.exists())
		{
			if(!dataFolder.mkdir())
			{
				System.out.println("Falha ao criar pasta raiz.");
				System.exit(-1);
			}
		}
		
		saveFolder = new File(dataFolder + "\\saves");
		
		if(!saveFolder.exists())
		{
			if(!saveFolder.mkdir())
			{
				System.out.println("Falha ao criar pasta de saves");
				System.exit(-1);
			}
		}
	}
	
	public static File getDataFolder()
	{
		return dataFolder;
	}
	
	public static GameData loadGame(String worldName)
	{
		return null;
	}
	
	public static void saveGame(GameData data)
	{
		
	}
}
