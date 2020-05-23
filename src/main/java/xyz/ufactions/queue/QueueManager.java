package xyz.ufactions.queue;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class QueueManager {

    private final JavaPlugin plugin;
    private String disallowMessage = "Queued message";

    private final Map<QueuedUser, Long> queue;
    private int maxOnline = 1;

    public QueueManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.queue = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new QueueListener(this), plugin);

        plugin.getLogger().info("Started Queue...");
    }

    // Methods

    public boolean allowed(UUID uuid) {
        return true; // TODO FINISH
    }

    // Setters

    public void setDisallowMessage(String disallowMessage) {
        this.disallowMessage = disallowMessage;
    }

    public void setMaxOnline(int maxOnline) {
        this.maxOnline = maxOnline;
        plugin.getLogger().info("(Queue) Online limit set: " + maxOnline);
    }


    // Fetching Information

    /**
     * @param uuid The user's unique id
     * @return The queued user's position if in the queue
     */
    public int getPosition(UUID uuid) {
        QueuedUser user = getUser(uuid);
        if (user != null) {
            return user.getPosition();
        }
        return -1;
    }

    /**
     * @param uuid The user's unique id
     * @return The queued user if in the queue
     */
    public QueuedUser getUser(UUID uuid) {
        for (QueuedUser user : queue.keySet()) {
            if (user.getUUID() == uuid) {
                return user;
            }
        }
        return null;
    }

    /**
     * @return The number of players allowed on the server before the queue gets enabled
     */
    public int getMaxOnline() {
        return maxOnline;
    }

    /**
     * @return All of the users in the queue
     */
    public Set<QueuedUser> getQueue() {
        return queue.keySet();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getDisallowMessage() {
        return disallowMessage;
    }
}