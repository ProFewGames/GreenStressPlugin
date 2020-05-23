package xyz.ufactions.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ufactions.libs.ItemBuilder;

import java.util.List;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public abstract class OrderingButton<PluginType extends JavaPlugin> implements IButton {

    private ItemStack item;
    private int position;
    protected PluginType Plugin;

    public OrderingButton(PluginType plugin, Material material, String name) {
        Plugin = plugin;
        this.item = new ItemBuilder(material).name(name).build();
    }

    public OrderingButton(PluginType plugin, Material material, int data, String name) {
        Plugin = plugin;
        this.item = new ItemBuilder(material, data).name(name).build();
    }

    public OrderingButton(PluginType plugin, Material material, int data, String name, String... lore) {
        Plugin = plugin;
        this.item = new ItemBuilder(material, data).name(name).lore(lore).build();
    }

    public OrderingButton(PluginType plugin, Material material, int data, String name, List<String> lore) {
        Plugin = plugin;
        this.item = new ItemBuilder(material, data).name(name).lore(lore).build();
    }

    public OrderingButton(PluginType plugin, Material material, String name, String... lore) {
        Plugin = plugin;
        this.item = new ItemBuilder(material).name(name).lore(lore).build();
    }

    public OrderingButton(PluginType plugin, ItemStack item) {
        Plugin = plugin;

        this.item = item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPosition() {
        return position;
    }
}