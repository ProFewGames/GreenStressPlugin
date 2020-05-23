package xyz.ufactions.gsp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.F;
import xyz.ufactions.libs.UtilLoc;

public class StressCommand implements CommandExecutor {

    private final GreenStress plugin;

    public StressCommand(GreenStress plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.getConfiguration().reload();
                sender.sendMessage(F.main("GreenBull", "Successfully reloaded the configuration file."));
                return true;
            }
            if (args[0].equalsIgnoreCase("setspawn")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
                    return true;
                }
                Player player = (Player) sender;
                plugin.getData().set("spawn", UtilLoc.encodeLoc(player.getLocation()));
                player.sendMessage(F.main("GreenBull", "Spawn Set!"));
                return true;
            }
        }
        sender.sendMessage(F.help("/" + label + " setspawn", "Set the spawn where players will spawn."));
        sender.sendMessage(F.help("/" + label + " reload", "Reload the plugin configuration."));
        return true;
    }
}