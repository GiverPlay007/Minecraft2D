package me.giverplay.minecraft2D.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

public class GameSave
{
	private static final File dataFolder;
	private static final File saveFolder;
	
	private static boolean canLoad = false;
	
	static
	{
		dataFolder = new File(System.getProperty("user.home") + File.separator + "Craftzinho");
		
		if(!dataFolder.exists())
		{
			if(!dataFolder.mkdir())
			{
				System.out.println("Falha ao criar pasta raiz.");
				System.exit(1);
			}
		}
		
		saveFolder = new File(dataFolder + File.separator + "Saves");
		
		if(!saveFolder.exists())
		{
			if(!saveFolder.mkdir())
			{
				System.out.println("Falha ao criar pasta de saves");
				System.exit(1);
			}
		}
		
		File file = new File(saveFolder, "Save.dat");
		
		if(file.exists())
			canLoad = true;
	}
	
	public static File getDataFolder()
	{
		return dataFolder;
	}
	
	public static File getSaveFolder()
	{
		return saveFolder;
	}
	
	public static GameData loadGame(Game game, String worldName) throws IOException
	{
		File file = new File(getSaveFolder(), worldName + ".dat");
		
		if(!file.exists())
		{
			System.out.println("Arquivo de save não existe.");
			return null;
		}
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		GameData data = new GameData(game);
		data.setName(worldName);
		
		try
		{
			data.deserializeAndApply(br.readLine());
		}
		catch(JSONException e)
		{
			System.out.println("Falha ao decodificar o save");
			return null;
		}
		finally
		{
			br.close();
		}
		
		return data;
	}
	
	public static void saveGame(GameData data) throws IOException
	{
		File file = new File(saveFolder, data.getName() + ".dat");
		
		if(file.exists())
			file.delete();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(saveFolder, data.getName() + ".dat")));
		
		try
		{
			writer.write(data.serialize());
		}
		catch(JSONException e)
		{
			System.out.println("Falha ao serializar e salvar o mundo");
		}
		finally
		{
			writer.close();
			canLoad = true;
		}
	}

	public static boolean canLoad()
	{
		return canLoad;
	}
}
