package xyz.ufactions.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public abstract class InverseButton<PluginType extends JavaPlugin> extends ShopItem<PluginType> {

    private InverseButton<PluginType> reverse;

    public InverseButton(PluginType plugin, Material material, String name, int position, InverseButton<PluginType> reverseButton, String... lore) {
        this(plugin, material, 0, name, position, reverseButton, lore);
    }

    public InverseButton(PluginType plugin, Material material, int data, String name, int position,
                         InverseButton<PluginType> reverseButton, String... lore) {
        super(plugin, material, data, name, position, lore);

        this.reverse = reverseButton;
    }

    public InverseButton(PluginType plugin, Material material, String name, int position, String... lore) {
        super(plugin, material, 0, name, position, lore);
    }

    public InverseButton(PluginType plugin, Material material, int data, String name, int position, String... lore) {
        super(plugin, material, data, name, position, lore);
    }

    public boolean canInverse(Player player) {
        return true;
    }

    public void setReverse(InverseButton<PluginType> reverse) {
        this.reverse = reverse;
    }

    public InverseButton<PluginType> getReverse() {
        return reverse;
    }
}