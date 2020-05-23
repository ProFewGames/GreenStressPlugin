package xyz.ufactions.gsp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.gsp.command.FlyCommand;
import xyz.ufactions.gsp.command.SpawnCommand;
import xyz.ufactions.gsp.command.StressCommand;
import xyz.ufactions.gsp.file.ConfigurationFile;
import xyz.ufactions.gsp.file.DataFile;
import xyz.ufactions.gsp.listener.PlayerListener;
import xyz.ufactions.gsp.listener.WorldListener;
import xyz.ufactions.libs.UtilLoc;
import xyz.ufactions.queue.QueueManager;

public class GreenStress extends JavaPlugin {

    private QueueManager queue;
    private WorldListener worldListener;

    private ConfigurationFile config;
    private DataFile data;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(worldListener = new WorldListener(this), this);
        this.queue = new QueueManager(this); // Run the server queue

        this.config = new ConfigurationFile(this);
        this.data = new DataFile(this);

        getCommand("stress").setExecutor(new StressCommand(this));
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        // Start flying particles
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.isFlying()) {
                    player.spawnParticle(Particle.CLOUD, player.getLocation(), 5);
                }
            }
        }, 5L, 5L);
    }

    public Location getSpawn() {
        if (data.getString("spawn") != null) {
            return UtilLoc.decodeLoc(data.getString("spawn"));
        } else {
            return Bukkit.getWorld(config.getFallbackWorld()).getSpawnLocation();
        }
    }

    public ConfigurationFile getConfiguration() {
        return config;
    }

    public DataFile getData() {
        return data;
    }

    public QueueManager getQueue() {
        return queue;
    }

    public WorldListener getWorldListener() {
        return worldListener;
    }
}