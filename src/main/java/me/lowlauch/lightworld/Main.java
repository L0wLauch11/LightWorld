package me.lowlauch.lightworld;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private static String prefix = "[§lLightWorld§r] ";
	
	public static String getPrefix()
	{
		return prefix;
	}

	public void onEnable()
	{
		this.getCommand("lv").setExecutor(new Commands());
		this.getLogger().info("Enabled!");
	}
	
	public void onDisable()
	{
		this.getLogger().info("Disabled");
	}
}
