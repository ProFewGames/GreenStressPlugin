package xyz.ufactions.gsp;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.gsp.command.*;
import xyz.ufactions.gsp.file.ConfigurationFile;
import xyz.ufactions.gsp.file.DataFile;
import xyz.ufactions.gsp.listener.PlayerListener;
import xyz.ufactions.gsp.listener.WorldListener;
import xyz.ufactions.libs.*;
import xyz.ufactions.monitor.LagMeter;
import xyz.ufactions.queue.QueueManager;
import xyz.ufactions.updater.Updater;
import xyz.ufactions.weather.WeatherModule;

public class GreenStress extends JavaPlugin {

    private WeatherModule weather;
    private QueueManager queue;
    private WorldListener worldListener;

    private World world;

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
        getCommand("reset").setExecutor(new ResetCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("world").setExecutor(new WorldCommand(this));
        getCommand("lag").setExecutor(new LagCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        new Updater(this); // Start Updater

        LagMeter.initialize(this);

        this.weather = new WeatherModule(this, true);
    }

    @Override
    public void onDisable() {
        deleteOldWorld(true);
    }

    public void generateNew(boolean notify) {
        generateNew(null, notify);
    }

    public void generateNew(Callback<World> callback, boolean notify) {
        if (notify) {
            Bukkit.broadcastMessage(C.Strip);
            Bukkit.broadcastMessage(C.mBody + "RESETTING WORLD!");
            Bukkit.broadcastMessage(C.Strip);
        }
        if (world != null) {
            world.getPlayers().forEach(player -> player.teleport(getSpawn()));
        }
        Bukkit.getScheduler().runTaskLater(this, () -> {
            deleteOldWorld(notify);
            if (notify) {
                Bukkit.broadcastMessage(F.main("GreenBull", "Generating new world"));
            }
            long epoch = System.currentTimeMillis();
//            this.world = Bukkit.createWorld(new WorldCreator("stress"));
            this.world = WorldUtil.fastCreateWorld(new WorldCreator("stress"));
            if (world == null) {
                if (notify) {
                    Bukkit.broadcastMessage(F.main("GreenBull", "hmmm... Something went wrong notify a server admin!"));
                }
            } else {
                if (notify) {
                    Bukkit.broadcastMessage(F.main("GreenBull", "World " + F.elem(world.getName()) + " generated in " + F.elem(String.valueOf(System.currentTimeMillis() - epoch)) + "ms."));
                }
                Bukkit.getOnlinePlayers().forEach(player -> player.teleport(world.getSpawnLocation()));
            }
            if (callback != null)
                callback.run(world);
        }, 5L);
    }

    public void deleteOldWorld(boolean notify) {
        if (world != null) {
            if (notify) {
                Bukkit.broadcastMessage(F.main("GreenBull", "Deleting previous world"));
            }
            world.getPlayers().forEach(player -> player.teleport(getSpawn()));
            Bukkit.unloadWorld(world, false);
            try {
                FileUtils.deleteDirectory(world.getWorldFolder());
                if (notify) {
                    Bukkit.broadcastMessage(F.main("GreenBull", "Previous world deleted"));
                }
            } catch (Exception e) {
                if (notify) {
                    Bukkit.broadcastMessage(F.error("GreenBull", "Something went wrong... notify a server administrator!"));
                }
                e.printStackTrace();
            }
        }
    }

    public World getWorld() {
        return world;
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

    public WeatherModule getWeather() {
        return weather;
    }
}