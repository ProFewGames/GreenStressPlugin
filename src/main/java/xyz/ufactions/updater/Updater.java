package xyz.ufactions.updater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.updater.event.UpdateEvent;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class Updater {

    public Updater(JavaPlugin plugin) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::mainThreadHeartbeat, 0L, 1L);
    }

    public void mainThreadHeartbeat() {
        for (UpdateType type : UpdateType.values()) {
            if (type.Elapsed()) {
                Bukkit.getServer().getPluginManager().callEvent(new UpdateEvent(type));
            }
        }
    }
}