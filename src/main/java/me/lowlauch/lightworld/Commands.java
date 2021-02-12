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
                if(args[0].equalsIgnoreCase("help"))
                {
                    commandSender.sendMessage(Main.getPrefix() + "§nCommands§r:" +
                            "§l/lw tp§r <world> [player] [x] [y] [z] - §oTeleports a player to a specific world at specific coordinatesn§r\n" +
                            "§l/lw create§r <world> [flat, void, normal] - §oCreates a new world!§r\n" +
                            "§l/lw delete§r <world> - §oDeletes the provided world§r\n" +
                            "§l/lw import§r <world> <void,flat,normal> - §oImports the provided world§r\n" +
                            "§l/lw info§r - §oCredits :)§r" +
                            "§l/lw autoimport§r <world> <void,flat,normal> - §oImports the world automatically on server restart (or reload)§r");
                }

                if(args[0].equalsIgnoreCase("info"))
                {
                    commandSender.sendMessage(Main.getPrefix() + "LightWorld by @L0wLauch11");
                }

                if(args[0].equalsIgnoreCase("autoimport"))
                {
                    if(args.length == 1)
                    {
                        commandSender.sendMessage(Main.getPrefix() + "You have to specify a world!");
                    } else
                    {
                        int getMapsCount = Main.getInstance().getConfig().getInt("lightworld.maps.count");
                        int mapId = getMapsCount+1;

                        Main.getInstance().getConfig().set("lightworld.maps.map" + mapId, args[1]);
                        if(args[2].equalsIgnoreCase("void"))
                        {
                            Main.getInstance().getConfig().set("lightworld.maps.map" + mapId + "isVoid", true);
                        }
                        Main.getInstance().getConfig().set("lightworld.maps.count", mapId);
                        commandSender.sendMessage(Main.getPrefix() + "The world §l" + args[1] + "§r will be loaded on every restart automatically!");
                        Main.getInstance().saveConfig();
                        Main.getInstance().reloadConfig();
                    }
                }

                if(args[0].equalsIgnoreCase("delete") && commandSender.isOp())
                {
                    World world = Bukkit.getWorld(args[1]);
                    if(world != null)
                    {
                        Bukkit.getServer().unloadWorld(world, true);
                        deleteWorld(world.getWorldFolder());
                        commandSender.sendMessage(Main.getPrefix() + "Farewell, " + args[1] + " D:");
                    } else
                    {
                        commandSender.sendMessage(Main.getPrefix() + "This world doesn't exist!");
                    }
                }

                if(args[0].equalsIgnoreCase("import"))
                {
                    if(args.length == 3)
                    {
                        if(args[2].equalsIgnoreCase("void"))
                        {
                            World world = Bukkit.getWorld(args[1]);
                            WorldCreator creator = new WorldCreator(args[1]);
                            final WorldCreator generator = creator.generator(new ChunkGenerator() {

                                public byte[] generate(World world, Random random, int x, int z) {
                                    return new byte[32768 * 256]; //Empty byte array
                                }
                            });
                            world = creator.createWorld();
                            commandSender.sendMessage(Main.getPrefix() + args[1] + " was loaded sucessfully (void)!");
                        } else {
                            new WorldCreator(args[1]).createWorld();
                            commandSender.sendMessage(Main.getPrefix() + "The world " + args[1] + " was loaded!");
                        }
                    } else
                    {
                        commandSender.sendMessage(Main.getPrefix() + "Wrong arguments!");
                    }
                }

                if(args[0].equalsIgnoreCase("create") && commandSender.isOp() && args.length >= 2)
                {
                    if(args.length == 2)
                    {
                        World world = Bukkit.getWorld(args[1]);
                        if(world == null)
                        {
                            WorldCreator wc = new WorldCreator(args[1]);
                            world = Bukkit.createWorld(new WorldCreator(args[1]));
                            commandSender.sendMessage(Main.getPrefix() + args[1] + " was generated sucessfully!");
                        } else
                        {
                            commandSender.sendMessage(Main.getPrefix() + "This World already exists! If you want to visit it, you must type §4/lv tp " + args[1] + "§f!");
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
                                commandSender.sendMessage(Main.getPrefix() + args[1] + " was generated sucessfully!");
                            } else
                            {
                                commandSender.sendMessage(Main.getPrefix() + "This World already exists! If you want to visit it, you must type §4/lv tp " + args[1] + "§f!");
                            }
                        }

                        if(args[2].equalsIgnoreCase("void"))
                        {
                            World world = Bukkit.getWorld(args[1]);
                            if(world == null)
                            {
                                WorldCreator creator = new WorldCreator(args[1]);
                                WorldCreator generator = creator.generator(new ChunkGenerator() {

                                    public byte[] generate(World world, Random random, int x, int z) {
                                        return new byte[32768 * 256]; //Empty byte array
                                    }
                                });
                                world = creator.createWorld();
                                commandSender.sendMessage(Main.getPrefix() + args[1] + " was generated sucessfully!");
                            } else
                            {
                                commandSender.sendMessage(Main.getPrefix() + "This World already exists! If you want to visit it, you must type §4/lv tp " + args[1] + "§f!");
                            }
                        }

                        if(args[2].equalsIgnoreCase("normal"))
                        {
                            World world = Bukkit.getWorld(args[1]);
                            if(world == null)
                            {
                                WorldCreator wc = new WorldCreator(args[1]);
                                world = Bukkit.createWorld(new WorldCreator(args[1]));
                                commandSender.sendMessage(Main.getPrefix() + args[1] + " was generated sucessfully!");
                            } else
                            {
                                commandSender.sendMessage(Main.getPrefix() + "This World already exists! If you want to visit it, you must type §4/lv tp " + args[1] + "§f!");
                            }
                        }
                    }
                } else if(args[0].equalsIgnoreCase("create"))
                {
                    commandSender.sendMessage(Main.getPrefix() + "Wrong arguments!");
                }

                if(args[0].equalsIgnoreCase("tp") && commandSender.isOp() || args[0].equalsIgnoreCase("tp") && !(commandSender instanceof Player))
                {
                    if(args.length == 3)
                    {
                        Player p = Bukkit.getPlayer(args[2]);
                        World world = Bukkit.getWorld(args[1]);
                        Location loc = new Location(world, 0, 50, 0);
                        p.teleport(loc);
                        p.sendMessage(Main.getPrefix() + "You were teleported to the world " + args[1] +  ".");
                        commandSender.sendMessage(Main.getPrefix() + "You teleported " + args[2] + " to the world " + args[1] +  " teleportiert.");
                    }

                    if(args.length == 2)
                    {
                        Player p = (Player) commandSender;
                        World world = Bukkit.getWorld(args[1]);
                        Location loc = new Location(world, 0, 50, 0);
                        p.teleport(loc);
                        p.sendMessage(Main.getPrefix() + "You were teleported to the world" + args[1] + ".");
                    }

                    if(args.length == 5)
                    {
                        Player p = (Player) commandSender;
                        World world = Bukkit.getWorld(args[1]);
                        Location loc = new Location(world, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                        p.teleport(loc);
                        p.sendMessage(Main.getPrefix() + "You were teleported to the world" + args[1] + " : " + args[2] + "x " + args[3] + "y " + args[4] + "z.");
                    }

                    if(args.length == 6)
                    {
                        Player p =  Bukkit.getPlayer(args[2]);
                        World world = Bukkit.getWorld(args[1]);
                        Location loc = new Location(world, Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                        p.teleport(loc);
                        p.sendMessage(Main.getPrefix() + "You were teleported to the world " + args[1] + " : " + args[3] + "x " + args[4] + "y " + args[5] + "z.");
                        commandSender.sendMessage(Main.getPrefix() + "You teleported " + args[2] + " to the world " + args[1] + " : " + args[3] + "x " + args[4] + "y " + args[5] + "z.");
                    }
                }
            } else
            {
                commandSender.sendMessage(Main.getPrefix() + "Wrong arguments!");
            }
            return true;
        }

        return false;
    }

}
