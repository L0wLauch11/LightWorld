package me.lowlauch.lightworld;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private static String prefix = "[§lLightWorld§r] ";
	private static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}
	
	public static String getPrefix()
	{
		return prefix;
	}

	public void onEnable()
	{
		instance = this;
		this.getCommand("lw").setExecutor(new Commands());
		this.getLogger().info("Enabled!");
	}
	
	public void onDisable()
	{
		this.getLogger().info("Disabled");
	}
}
