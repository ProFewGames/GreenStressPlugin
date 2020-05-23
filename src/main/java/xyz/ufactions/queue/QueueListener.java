package xyz.ufactions.queue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class QueueListener implements Listener {

    private final QueueManager manager;

    public QueueListener(QueueManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        if (!manager.allowed(e.getUniqueId())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, manager.getDisallowMessage());
        } else {
            e.allow();
        }
    }
}