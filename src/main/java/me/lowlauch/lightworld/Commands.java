package me.lowlauch.lightworld;

import java.io.File;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor
{
	
	public boolean deleteWorld(File path)
	{
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String commandInput, String[] args)
	{
		if(commandInput.equalsIgnoreCase("lw"))
		{
			if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("info"))
				{
					commandSender.sendMessage(Main.getPrefix() + "LightWorld-1.0 von L0wLauch11 uwu <3");
				}
				
				if(args[0].equalsIgnoreCase("delete"))
				{
				    World world = Bukkit.getWorld(args[1]);
				    if(!world.equals(null))
				    {
				        Bukkit.getServer().unloadWorld(world, true);
				        deleteWorld(world.getWorldFolder());
					    commandSender.sendMessage(Main.getPrefix() + "Lebewohl, " + args[1] + " D:");
				    } else
				    {
				    	commandSender.sendMessage(Main.getPrefix() + "Die Welt gibts nicht du kek!");
				    }
				}
				
				if(args[0].equalsIgnoreCase("create") && args.length >= 2)
				{
					if(args.length == 2)
					{
						World world = Bukkit.getWorld(args[1]);
						if(world == null)
						{
							WorldCreator wc = new WorldCreator(args[1]);
							world = Bukkit.createWorld(new WorldCreator(args[1]));
							commandSender.sendMessage(Main.getPrefix() + args[1] + " wurde erfolgreich generiert!");
						} else
						{
							commandSender.sendMessage(Main.getPrefix() + "Diese Welt existiert schon! Wenn du sie betreten willst, musst du §4/lv tp " + args[1] + "§f eingeben!");
						}
					}
					
					if(args.length == 3)
					{
						if(args[2].equalsIgnoreCase("flat"))
						{
							World world = Bukkit.getWorld(args[1]);
							if(world == null)
							{
								WorldCreator wc = new WorldCreator(args[1]);
								wc.type(WorldType.FLAT);
							    wc.environment(Environment.NORMAL);
								world = Bukkit.createWorld(wc);
								commandSender.sendMessage(Main.getPrefix() + args[1] + " wurde erfolgreich generiert!");
							} else
							{
								commandSender.sendMessage(Main.getPrefix() + "Diese Welt existiert schon! Wenn du sie betreten willst, musst du §4/lv tp " + args[1] + "§f eingeben!");
							}
						}
						
						if(args[2].equalsIgnoreCase("void"))
						{
							World world = Bukkit.getWorld(args[1]);
							if(world == null)
							{
								WorldCreator creator = new WorldCreator(args[1]);
								creator.generator(new ChunkGenerator() {
								    @Override
								    public byte[] generate(World world, Random random, int x, int z) {
								        return new byte[32768]; //Empty byte array
								    }
								});
								world = creator.createWorld();
								commandSender.sendMessage(Main.getPrefix() + args[1] + " wurde erfolgreich generiert!");
							} else
							{
								commandSender.sendMessage(Main.getPrefix() + "Diese Welt existiert schon! Wenn du sie betreten willst, musst du §4/lv tp " + args[1] + "§f eingeben!");
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("create"))
				{
					commandSender.sendMessage(Main.getPrefix() + "Falsche Argumente!");
				}
				
				if(args[0].equalsIgnoreCase("tp"))
				{
					if(args.length == 3)
					{
						Player p = Bukkit.getPlayer(args[2]);
						World world = Bukkit.getWorld(args[1]);
						Location loc = new Location(world, 0, 50, 0);
						p.teleport(loc);
						p.sendMessage(Main.getPrefix() + "Du wurdest zur Welt " + args[1] +  " teleportiert.");
						commandSender.sendMessage(Main.getPrefix() + "Du hast" + args[2] + " zur Welt " + args[1] +  " teleportiert.");
					}
					
					if(args.length == 2)
					{
						Player p = (Player) commandSender;
						World world = Bukkit.getWorld(args[1]);
						Location loc = new Location(world, 0, 50, 0);
						p.teleport(loc);
						p.sendMessage(Main.getPrefix() + "Du hast dich zur Welt " + args[1] + " teleportiert.");
					}
					
					if(args.length == 5)
					{
						Player p = (Player) commandSender;
						World world = Bukkit.getWorld(args[1]);
						Location loc = new Location(world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
						p.teleport(loc);
						p.sendMessage(Main.getPrefix() + "Du hast dich zur Welt " + args[1] + " bei den Koordinaten " + args[2] + " " + args[3] + " " + args[4] + " teleportiert.");
					}
					
					if(args.length == 6)
					{
						Player p =  Bukkit.getPlayer(args[2]);
						World world = Bukkit.getWorld(args[1]);
						Location loc = new Location(world, Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
						p.teleport(loc);
						p.sendMessage(Main.getPrefix() + "Du wurdest zur Welt " + args[1] + " bei den Koordinaten " + args[3] + " " + args[4] + " " + args[5] + " teleportiert.");
						commandSender.sendMessage(Main.getPrefix() + "Du hast " + args[2] + " zur Welt " + args[1] + " bei den Koordinaten " + args[3] + " " + args[4] + " " + args[5] + " teleportiert.");
					}
				}
			} else
			{
				commandSender.sendMessage(Main.getPrefix() + "Falsche Argumente!");
			}
			return true;
		}
			
		return false;
	}

}
