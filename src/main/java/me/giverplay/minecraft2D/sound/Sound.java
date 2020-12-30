package me.giverplay.minecraft2D.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
	public static Clips coin = load("/coin.wav");
	public static Clips hit = load("/hit.wav");
	public static Clips lose = load("/lose.wav");
	public static Clips hit2 = load("/hit2.wav");
	public static Clips jump = load("/jump.wav");
	public static Clips life = load("/life.wav");
	public static Clips up = load("/up.wav");

	public static void init() // Fix first sound lag
	{ }

	public static class Clips
	{
		private final int count;

		public Clip[] clips;
		private int index;

		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException
		{
			clips = new Clip[count];

			this.count = count;

			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}

		public void play()
		{
			clips[index].stop();
			clips[index].setFramePosition(0);
			clips[index].start();

			index++;

			if(index >= count) {
				index = 0;
			}
		}

		public void loop()
		{
			clips[index].loop(300);
		}
	}

	private static Clips load(String name)
	{
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name)))
		{
			byte[] buffer = new byte[1024];

			int read;

			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}

			byte[] data = baos.toByteArray();
			return new Clips(data, 1);

		} catch(Throwable throwable) {
			return null;
		}
	}
}