package xyz.ufactions.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.libs.ItemBuilder;

import java.util.List;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public abstract class ShopItem<PluginType extends JavaPlugin> implements IButton {

    protected ItemStack item;
    private final int position;
    protected PluginType Plugin;

    public ShopItem(PluginType plugin, Material material, String name, int position) {
        this(plugin, material, 0, name, position);
    }

    public ShopItem(PluginType plugin, Material material, String name, int position, List<String> lore) {
        this(plugin, material, 0, name, position, lore.toArray(new String[0]));
    }

    public ShopItem(PluginType plugin, Material material, int data, String name, int position) {
        this(plugin, material, data, name, position, "");
    }

    public ShopItem(PluginType plugin, Material material, String name, int position, String... lore) {
        this(plugin, material, 0, name, position, lore);
    }

    public ShopItem(PluginType plugin, Material material, int data, String name, int position, String... lore) {
        this(plugin, position, new ItemBuilder(material, data).name(name).lore(lore).build());
    }

    public ShopItem(PluginType plugin, int position, ItemStack item) {
        Plugin = plugin;
        this.item = item;
        this.position = position;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPosition() {
        return position;
    }

}