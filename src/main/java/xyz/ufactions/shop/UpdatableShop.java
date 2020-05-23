package xyz.ufactions.shop;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public abstract class UpdatableShop extends Shop {

    public UpdatableShop(JavaPlugin plugin, String name, int length, ShopFiller filler, IButton... items) {
        super(plugin, name, length, filler, items);

    }

    public UpdatableShop(JavaPlugin plugin, String name, int length, ShopFiller filler, List<IButton> items) {
        super(plugin, name, length, filler, items);
    }

    public UpdatableShop(JavaPlugin plugin, Shop returnShop, String name, int length, ShopFiller filler,
                         IButton... items) {
        super(plugin, returnShop, name, length, filler, items);
    }

    @Override
    public final void onClose(Player player) {
        if (!allowOverride) {
            cleanUp(player);
        }
    }

    public void cleanUp(Player player) {
    }

    private boolean allowOverride = false;

    @Override
    public void onClick(Player player, IButton button) {
        allowOverride = true;
        update();
        allowOverride = false;
    }

    public final void updateName(Player player, String title) {
        // TODO n/a version
    }

    public abstract void update();
}