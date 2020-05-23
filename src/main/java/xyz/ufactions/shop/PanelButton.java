package xyz.ufactions.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.ufactions.libs.ItemBuilder;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
// TODO : Add support for rainbow panels from MegaBukkit
public class PanelButton implements IButton {

    private Integer[] colors = {3, 4, 0};
    private ItemStack item;
    private int position;

    public PanelButton(int position) {
        item = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).build();
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
    }
}