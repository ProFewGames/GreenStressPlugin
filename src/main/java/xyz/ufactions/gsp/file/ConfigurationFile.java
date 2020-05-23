package xyz.ufactions.gsp.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ufactions.gsp.GreenStress;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationFile {

    private final GreenStress plugin;

    private File file;
    private FileConfiguration config;

    public ConfigurationFile(GreenStress plugin) {
        this.plugin = plugin;

        reload();
    }

    public List<String> joinMessageContent() {
        return config.getStringList("join.message.content");
    }

    public String joinTitleTitle() {
        return config.getString("join.title.title");
    }

    public String joinTitleSubtitle() {
        return config.getString("join.title.subtitle");
    }

    public String getFallbackWorld() {
        return config.getString("fallback-world");
    }

    public int getMaxPlayers() {
        return config.getInt("max-players");
    }

    public boolean joinTitleEnabled() {
        return config.getBoolean("join.title.enabled");
    }

    public boolean joinMessageEnabled() {
        return config.getBoolean("join.message.enabled");
    }

    public boolean settingPlayerVisibility() {
        return config.getBoolean("settings.player-visibility");
    }

    public boolean settingDaylight() {
        return config.getBoolean("settings.daylightCycle");
    }

    public boolean settingWeather() {
        return config.getBoolean("settings.weatherCycle");
    }

    public boolean settingPvE() {
        return config.getBoolean("settings.pve");
    }

    public boolean settingPvP() {
        return config.getBoolean("settings.pvp");
    }

    public void reload() {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        plugin.getQueue().setMaxOnline(getMaxPlayers());
        plugin.getWorldListener().updateHandler();
        plugin.getLogger().info("Reloaded configuration");
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save configuration file:");
            e.printStackTrace();
        }
    }
}