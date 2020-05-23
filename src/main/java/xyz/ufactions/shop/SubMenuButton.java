package xyz.ufactions.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class SubMenuButton<PluginType extends JavaPlugin> extends ShopItem<PluginType> {

    private Shop menu;

    public SubMenuButton(PluginType plugin, Shop menu, Material material, String name, int position, List<String> lore) {
        super(plugin, material, name, position, lore);

        this.menu = menu;
    }

    public SubMenuButton(PluginType plugin, Shop menu, Material material, String name, int position) {
        super(plugin, material, name, position);
        this.menu = menu;
    }

    public SubMenuButton(PluginType plugin, Shop menu, Material material, int data, String name, int position) {
        super(plugin, material, data, name, position);
        this.menu = menu;
    }

    public SubMenuButton(PluginType plugin, Shop menu, Material material, String name, int position, String... lore) {
        super(plugin, material, name, position, lore);
        this.menu = menu;
    }

    public SubMenuButton(PluginType plugin, Shop menu, Material material, int data, String name, int position, String... lore) {
        super(plugin, material, data, name, position, lore);
        this.menu = menu;
    }

    public SubMenuButton(PluginType plugin, Shop menu, int position, ItemStack item) {
        super(plugin, position, item);
        this.menu = menu;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        menu.openInventory(player);
    }
}