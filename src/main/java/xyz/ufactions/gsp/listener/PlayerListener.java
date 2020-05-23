package xyz.ufactions.gsp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.TitleAPI;

public class PlayerListener implements Listener {

    private final GreenStress plugin;

    public PlayerListener(GreenStress plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvE(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                if (!plugin.getConfiguration().settingPvP()) {
                    e.setCancelled(true);
                }
            } else {
                if (!plugin.getConfiguration().settingPvE()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null); // Disable join message so it won't mess with our join message

        Player player = e.getPlayer();
        player.teleport(plugin.getSpawn());

        if (plugin.getConfiguration().joinMessageEnabled()) {
            for (String string : plugin.getConfiguration().joinMessageContent()) {
                player.sendMessage(C.color(string));
            }
        }

        if (!plugin.getConfiguration().settingPlayerVisibility()) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(plugin, other);
                other.hidePlayer(plugin, player);
            }
        }

        if (plugin.getConfiguration().joinTitleEnabled()) {
            TitleAPI.sendTitle(player, C.color(plugin.getConfiguration().joinTitleTitle()), C.color(plugin.getConfiguration().joinTitleSubtitle()), 20, 20, 20);
        }
    }
}