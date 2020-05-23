package xyz.ufactions.gsp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.F;
import xyz.ufactions.libs.InputRequest;

public class ResetCommand implements CommandExecutor {

    private final GreenStress plugin;

    public ResetCommand(GreenStress plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
            return true;
        }
        Player player = (Player) sender;
        InputRequest.confirmationInput(value -> {
            if (value) {
                plugin.generateNew(true);
            } else {
                player.sendMessage(F.error("GreenBull", "Did not confirm"));
            }
        }, "Reset", null, plugin, player);
        return true;
    }
}