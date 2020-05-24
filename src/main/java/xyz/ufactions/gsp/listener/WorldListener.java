package xyz.ufactions.gsp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.weather.WeatherOptions;

public class WorldListener implements Listener {

    private final GreenStress plugin;

    public WorldListener(GreenStress plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldInitialize(WorldInitEvent e) {
        plugin.getLogger().info("Manipulating world : " + e.getWorld().getName());
        WeatherOptions options = plugin.getWeather().getWeather(e.getWorld());
        options.setDisableRain(plugin.getConfiguration().settingWeather());
        options.setLockedTime(6000);
        options.setTimeLock(plugin.getConfiguration().settingDaylight());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        plugin.getLogger().info("Unloading world : " + e.getWorld().getName());
        plugin.getWeather().unloadWeather(e.getWorld());
    }

//    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

//    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }
}