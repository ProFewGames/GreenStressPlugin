package xyz.ufactions.gsp.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.F;

public class WorldCommand implements CommandExecutor {

    private final GreenStress plugin;

    public WorldCommand(GreenStress plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                player.sendMessage(F.main("GreenBull", "Available Worlds:"));
                for (World world : Bukkit.getWorlds()) {
                    player.sendMessage(F.list(world.getName()));
                }
                return true;
            }
            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                player.sendMessage(F.error("GreenBull", "World " + F.elem(args[0]) + C.cRed + " does not exist."));
                return true;
            }
            if (world.getName().equalsIgnoreCase(plugin.getConfiguration().getFallbackWorld())) {
                player.teleport(plugin.getSpawn());
            } else {
                player.teleport(world.getSpawnLocation());
            }
            player.sendMessage(F.main("GreenBull", "You've teleported to world " + F.elem(world.getName()) + "."));
            return true;
        }
        player.sendMessage(F.main("GreenBull", "You are in the world " + F.elem(player.getWorld().getName()) + "."));
        return true;
    }
}