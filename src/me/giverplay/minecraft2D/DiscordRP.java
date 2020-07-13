package me.giverplay.minecraft2D;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP
{
	private boolean running = false;
	private long created = 0;
	
	public void start()
	{
		created = System.currentTimeMillis();
		
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback()
		{
			@Override
			public void apply(DiscordUser user)
			{
				update("Initializing", "...");
			}
		}).build();
		
		DiscordRPC.discordInitialize("731973126346375169", handlers, true);
		
		new Thread("Discord Callback")
		{
			
			@Override
			public void run() 
			{
				while(running)
				{
					DiscordRPC.discordRunCallbacks();
				}
			};
			
		}.start();
	}
	
	public void shutdown()
	{
		running = false;
		DiscordRPC.discordShutdown();
	}
	
	public void update(String line1, String line2)
	{
		DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(line2);
		builder.setBigImage("maior", "Craftzinho");
		builder.setDetails(line1);
		builder.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(builder.build());
	}
}
