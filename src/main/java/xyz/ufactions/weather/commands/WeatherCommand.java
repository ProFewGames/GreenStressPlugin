package xyz.ufactions.weather.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ufactions.libs.F;
import xyz.ufactions.weather.WeatherModule;
import xyz.ufactions.weather.ui.WeatherUI;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class WeatherCommand implements CommandExecutor {

    private final WeatherModule module;

    public WeatherCommand(WeatherModule module) {
        this.module = module;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("weather.command")) {
            sender.sendMessage(F.noPermission());
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(F.error("GreenBull", "You must be a player in-game to execute this command."));
            return true;
        }
        Player player = (Player) sender;
        new WeatherUI(player, module).openInventory(player);
        return true;
    }
}