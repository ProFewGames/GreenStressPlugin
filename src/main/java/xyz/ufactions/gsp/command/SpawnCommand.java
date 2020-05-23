package xyz.ufactions.gsp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.F;

public class SpawnCommand implements CommandExecutor {

    private final GreenStress plugin;

    public SpawnCommand(GreenStress plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage(F.main("GreenBull", "Teleported to spawn..."));
        player.teleport(plugin.getSpawn());
        return true;
    }
}