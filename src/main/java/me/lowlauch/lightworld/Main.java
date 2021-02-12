package me.lowlauch.lightworld;

import org.bukkit.Bukkit;
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

        getConfig().addDefault("lightworld.maps", null);
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();

        int mapsCount = Main.getInstance().getConfig().getInt("lightworld.maps.count");
        for(int i=1; i<=mapsCount; i++)
        {
            String mapName = getConfig().getString("lightworld.maps.map" + i);
            boolean worldIsVoidMap = getConfig().getBoolean("lightworld.maps.map" + i + "isVoid");
            if(worldIsVoidMap)
            {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lw import " + mapName + " void");
            } else
            {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lw import " + mapName + " normal");
            }
            getLogger().info("Die Map " + mapName + " wurde importiert!");
        }
    }

    public void onDisable()
    {
        this.getLogger().info("Disabled");
    }
}
