package xyz.ufactions.libs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.shop.Shop;
import xyz.ufactions.shop.ShopItem;
import xyz.ufactions.updater.UpdateType;
import xyz.ufactions.updater.event.UpdateEvent;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class InputRequest {

    public static void confirmationInput(final Callback<Boolean> callback, final JavaPlugin plugin, final Player player) {
        confirmationInput(callback, null, plugin, player);
    }

    public static void confirmationInput(final Callback<Boolean> callback, final ItemStack display, final JavaPlugin plugin, final Player player) {
        confirmationInput(callback, "", display, plugin, player);
    }

    public static void confirmationInput(final Callback<Boolean> callback, final String additionalInfo, final ItemStack display, final JavaPlugin plugin, final Player player) {
        final boolean[] called = {false};

        Shop shop = new Shop(plugin, C.mHead + "Confirm " + additionalInfo, 27, Shop.ShopFiller.PANE,
                new ShopItem(plugin, Material.WOOL, 13, C.cGreen + C.Bold + "CONFIRM", 11) {

                    @Override
                    public void onClick(Player player, ClickType clickType) {
                        callback.run(true);
                        called[0] = true;
                        player.closeInventory();
                    }
                },
                new ShopItem(plugin, Material.WOOL, 14, C.cRed + C.Bold + "DENY", 15) {

                    @Override
                    public void onClick(Player player, ClickType clickType) {
                        callback.run(false);
                        called[0] = true;
                        player.closeInventory();
                    }
                }) {

            @Override
            public boolean canClose(Player player) {
                return called[0];
            }
        };

        if (display != null) {
            shop.addButton(new ShopItem(plugin, 13, display) {

                @Override
                public void onClick(Player player, ClickType clickType) {
                    player.sendMessage(F.error("Billy", "Hey! You can't take this item... it's just for display"));
                }
            });
        }

        shop.openInventory(player);
    }

    // TODO ADD REDUNDENCY | Spigot 1.
    public static void requestInput(final Callback<String> callback, final JavaPlugin plugin, final Player player) {
        final long start = System.currentTimeMillis();

        Listener listener = new Listener() {

            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent e) {
                if (e.getPlayer() == player) {
                    e.setCancelled(true);
                }
            }

            @EventHandler
            public void onUpdate(UpdateEvent e) {
                if (e.getType() != UpdateType.FAST) return;

                // Check timeout
                if (UtilTime.elapsed(start, 60000)) {
                    player.sendMessage(F.error("Timings", "Didn't receive any input for 60 seconds... Factory timing out"));
                    HandlerList.unregisterAll(this);
                    callback.run(null);
                } else {
                    TitleAPI.sendTitle(player, C.mHead + "Type input in chat", C.mBody + "Left click to exit", 0, 20, 0);
                }
            }

            @EventHandler
            public void onInteract(PlayerInteractEvent e) {
                if (UtilEvent.isAction(e, UtilEvent.ActionType.L) && e.getPlayer() == player) {
                    e.setCancelled(true);
                    HandlerList.unregisterAll(this);
                    callback.run(null);
                }
            }

            @EventHandler
            public void onChat(AsyncPlayerChatEvent e) {
                if (e.getPlayer() == player) {
                    e.setCancelled(true);
                    HandlerList.unregisterAll(this);
                    callback.run(ChatColor.stripColor(e.getMessage()));
                }
            }
        };

        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}