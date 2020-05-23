package xyz.ufactions.gsp.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class DataFile {

    private final JavaPlugin plugin;

    private final File file;
    private final FileConfiguration config;

    public DataFile(JavaPlugin plugin) {
        this.plugin = plugin;

        if(!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        this.file = new File(plugin.getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create data file:");
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        plugin.getLogger().info("Fetched data");
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data file:");
            e.printStackTrace();
        }
    }
}