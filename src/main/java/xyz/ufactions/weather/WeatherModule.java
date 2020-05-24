package xyz.ufactions.weather;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.updater.UpdateType;
import xyz.ufactions.updater.event.UpdateEvent;
import xyz.ufactions.weather.commands.WeatherCommand;

import java.io.File;
import java.util.HashSet;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class WeatherModule {

    private final GreenStress plugin;
    private final HashSet<WeatherOptions> weather = new HashSet<>();

    public WeatherModule(GreenStress plugin, boolean command) {
        this.plugin = plugin;

        // GreenBull - Weather load disabled since we're manipulating worlds
//        plugin.getServer().getScheduler().runTask(plugin, () -> {
//            if (plugin.getDataFolder().exists()) {
//                File file = new File(plugin.getDataFolder(), "data.yml");
//                if (file.exists()) {
//                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
//                    for (String string : config.getStringList("options")) {
//                        weather.add(WeatherOptions.decode(string));
//                    }
//                }
//            }
//        });

        if (command) {
            plugin.getCommand("weather").setExecutor(new WeatherCommand(this));
        }
    }

    public void save() {
        // GreenBull - Weather saving disabled since we're manipulating worlds
//        if (!plugin.getDataFolder().exists()) {
//            plugin.getDataFolder().mkdirs();
//        }
//        File file = new File(plugin.getDataFolder(), "data.yml");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
//        List<String> options = new ArrayList<>();
//        for (WeatherOptions wo : weather) {
//            options.add(wo.encode());
//        }
//        config.set("options", options);
//        try {
//            config.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public WeatherOptions getWeather(World world) {
        for (WeatherOptions weather : weather) {
            if (weather.getWorld() == world) {
                return weather;
            }
        }
        WeatherOptions weather = new WeatherOptions(world);
        this.weather.add(weather);
        return weather;
    }

    public void unloadWeather(World world) {
        this.weather.removeIf(weather -> weather.getWorld() == world);
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() != UpdateType.SEC)
            return;

        for (WeatherOptions weather : weather) {
            World world = weather.getWorld();
            if (weather.isTimeLock()) {
                world.setTime(weather.getLockedTime());
            }
            if (world.hasStorm() && weather.isDisableRain()) {
                world.setStorm(false);
            }
        }
    }

    public GreenStress getPlugin() {
        return plugin;
    }
}