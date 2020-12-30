package me.giverplay.minecraft2D.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
	private static final Random RANDOM = new Random();

	public static Clips explosion;
	public static Clips damage;
	public static Clips lose;
	public static Clips jump;
	public static Clips heal;
	public static Clips hit;

	public static void init() throws Throwable
	{
		explosion = load("explosion.wav", 4);
		damage = load("damage.wav", 1);
		heal = load("heal.wav", 1);
		jump = load("jump.wav", 2);
		lose = load("lose.wav", 1);
		hit = load("hit.wav", 1);
	}

	public static class Clips
	{
		public Clip[] clips;

		public Clips(byte[][] buffer) throws LineUnavailableException, IOException, UnsupportedAudioFileException
		{
			clips = new Clip[buffer.length];

			for(int i = 0; i < clips.length; i++)
			{
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer[i])));
			}
		}

		public void play()
		{
			play(false);
		}

		public void play(boolean loop)
		{
			int index = RANDOM.nextInt(clips.length);

			clips[index].stop();
			clips[index].setFramePosition(0);

			if(loop) {
				clips[index].loop(300);
			} else {
				clips[index].start();
			}
		}
	}

	private static Clips load(String nameBase, int count) throws IOException, LineUnavailableException, UnsupportedAudioFileException
	{
		byte[][] dataArray = new byte[count][];

		for(int i = 0; i < count; i++)
		{
			String name = "/sounds/" + nameBase + i;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));

			byte[] buffer = new byte[1024];
			int read;

			while((read = dis.read(buffer)) >= 1)
			{
				bos.write(buffer, 0, read);
			}

			dataArray[i] = bos.toByteArray();
			dis.close();
			bos.close();
		}

		return new Clips(dataArray);
	}
}