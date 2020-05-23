package xyz.ufactions.gsp.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.ufactions.gsp.GreenStress;

public class WorldListener implements Listener {

    private final GreenStress plugin;

    public WorldListener(GreenStress plugin) {
        this.plugin = plugin;
    }

    private void touchGameRule() {
        for (World world : Bukkit.getWorlds()) {
            if (!plugin.getConfiguration().settingDaylight())
                world.setTime(6000);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, plugin.getConfiguration().settingDaylight());
            if (!plugin.getConfiguration().settingWeather()) {
                world.setThundering(false);
            }
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, plugin.getConfiguration().settingWeather());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    public void updateHandler() {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            touchGameRule();
            plugin.getLogger().info("(WorldListener) Settings handled");
        }, 1);
    }
}