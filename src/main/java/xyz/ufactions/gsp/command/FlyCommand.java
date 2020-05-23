package xyz.ufactions.gsp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.libs.F;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
            return true;
        }
        Player player = (Player) sender;
        player.setAllowFlight(!player.getAllowFlight());
        if (player.getAllowFlight())
            player.setFlying(true);
        player.sendMessage(F.main("GreenBull", "You've " + F.ed(player.getAllowFlight()) + " fly!"));
        return true;
    }
}