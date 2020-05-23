package xyz.ufactions.gsp.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.gsp.gui.GreenGUI;
import xyz.ufactions.libs.*;

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
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
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

        player.getInventory().clear();

        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).glow(true).name(C.mHead + "Stressing Options").build());
        player.getInventory().setItem(22, new ItemBuilder(Material.CHEST).glow(true).name(C.mHead + "GREEN" + C.cWhite + "-" + C.mHead + "BULL " + C.cWhite + "(" + C.cGreen + "Information" + C.cWhite + ")")
                .lore("", C.cGray + "You're currently on the test.green-bull.net", C.cGray + "We hope you're satisfied with our services!", "", C.mHead + "WEBSITE " + C.cWhite + "» " + C.cDGreen + "green-bull.in",
                        C.mHead + "PANEL " + C.cWhite + "» " + C.cDGreen + "panel.green-bull.in", C.mHead + "DISCORD " + C.cWhite + "» " + C.cDGreen + "discord.gg/green-bull").build());

        player.getInventory().setItem(0, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(9, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(18, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(26, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(27, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        player.getInventory().setItem(35, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());

        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i) == null) {
                player.getInventory().setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name(" ").build());
            }
        }

        if (plugin.getConfiguration().joinTitleEnabled()) {
            TitleAPI.sendTitle(player, C.color(plugin.getConfiguration().joinTitleTitle()), C.color(plugin.getConfiguration().joinTitleSubtitle()), 20, 20, 20);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (UtilEvent.isAction(e, UtilEvent.ActionType.R) || UtilEvent.isAction(e, UtilEvent.ActionType.L)) {
            if (UtilInv.IsItem(e.getPlayer().getInventory().getItemInMainHand(), C.mHead + "Stressing Options", Material.COMPASS, (byte) 0))
                new GreenGUI(plugin).openInventory(e.getPlayer());
        }
    }
}