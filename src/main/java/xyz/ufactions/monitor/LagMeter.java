package xyz.ufactions.monitor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.updater.UpdateType;
import xyz.ufactions.updater.event.UpdateEvent;

public class LagMeter implements Listener {

    private long lastRun;

    private double ticksPerSecond;

    public static LagMeter instance;

    public static void initialize(JavaPlugin plugin) {
        if (instance == null)
            instance = new LagMeter(plugin);
    }

    private LagMeter(JavaPlugin plugin) {
        this.lastRun = System.currentTimeMillis();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void update(UpdateEvent event) {
        if (event.getType() != UpdateType.SEC)
            return;
        long now = System.currentTimeMillis();
        this.ticksPerSecond = 1000.0D / (now - this.lastRun) * 20.0D;
        this.lastRun = now;
    }

    public double getTicksPerSecond() {
        return this.ticksPerSecond;
    }
}
