package xyz.ufactions.gsp.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.F;
import xyz.ufactions.libs.ItemBuilder;
import xyz.ufactions.libs.Region;
import xyz.ufactions.shop.Shop;
import xyz.ufactions.shop.ShopItem;

public class GreenGUI extends Shop {

    public GreenGUI(GreenStress plugin) {
        super(plugin, "Stress TnT", 27, ShopFiller.RAINBOW,
                new ShopItem<GreenStress>(plugin, Material.TNT, C.cRed + C.Bold + "TNT ", 11, "* Click me to test TNT explosions *") {

                    @Override
                    public void onClick(Player player, ClickType type) {
                        player.closeInventory();
                        if (plugin.getWorld() != null) {
                            if (!player.getWorld().equals(plugin.getWorld())) {
                                player.teleport(plugin.getWorld().getSpawnLocation());
                            }
                        } else {
                            plugin.generateNew(world -> pasteTnT(plugin, player), true);
                            return;
                        }
                        pasteTnT(plugin, player);
                    }
                },
                new ShopItem<GreenStress>(plugin, Material.WATCH, C.cGreen + C.Bold + "Lag Tracker", 15, "* Click me to view server status *") {

                    @Override
                    public void onClick(Player player, ClickType clickType) {
                        player.closeInventory();
                        player.performCommand("lagassist:lagassist statsbar");
                    }
                });
    }

    private static void pasteTnT(GreenStress plugin, Player player) {
        player.getInventory().setItem(0, new ItemBuilder(Material.FLINT_AND_STEEL).name(C.mHead + "Ignition").build());

        player.sendMessage(F.main("GreenBull", "Calculating..."));

        Vector delta = new Vector(plugin.getConfiguration().stressTnTSize(), plugin.getConfiguration().stressTnTSize(), plugin.getConfiguration().stressTnTSize());
        BlockVector pos1 = new BlockVector(player.getLocation().toVector().subtract(delta));
        BlockVector pos2 = new BlockVector(player.getLocation().toVector().add(delta));
        Region region = new Region(pos1, pos2);
        BlockVector min = region.getMinimumPoint();
        BlockVector max = region.getMaximumPoint();

        player.sendMessage(F.main("GreenBull", "Placing TNT..."));
        long epoch = System.currentTimeMillis();

        int placed = 0;

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    player.getWorld().getBlockAt(x, y, z).setType(Material.TNT);
                    placed++;
                }
            }
        }

        player.sendMessage(F.main("GreenBull", "Finished task in: " + F.elem(String.valueOf(System.currentTimeMillis() - epoch)) + "ms. Placed " + F.elem(String.valueOf(placed)) + " blocks."));

        player.teleport(max.toLocation(player.getWorld()));

        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);

        for (String string : plugin.getConfiguration().testsTnTMessage()) {
            player.sendMessage(C.color(string));
        }
    }
}