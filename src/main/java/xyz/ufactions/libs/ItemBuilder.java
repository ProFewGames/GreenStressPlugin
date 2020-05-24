package xyz.ufactions.libs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 *
 */
public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(Material material) {
        this(material, 0);
    }

    public ItemBuilder(int id) {
        this(Material.getMaterial(id), 0);
    }

    public ItemBuilder(int id, int data) {
        this(Material.getMaterial(id), data);
    }

    public ItemBuilder(Material material, int data) {
        if (material == Material.MOB_SPAWNER) {
            this.item = new ItemStack(material);
            BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
            CraftCreatureSpawner spawner = (CraftCreatureSpawner) meta.getBlockState();
            meta.setBlockState(spawner);
            item.setItemMeta(meta);
        } else {
            this.item = new ItemStack(material, 1, (short) data);
        }
    }

    public static ItemStack constructPanel(boolean random) {
        return (new ItemBuilder(Material.STAINED_GLASS_PANE, (new Random()).nextInt(15))).name(" ").build();
    }

    public ItemBuilder glow(boolean glow) {
        if (glow) UtilInv.addDullEnchantment(this.item);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(cc(String.valueOf(name.startsWith("&") ? "&f" : "") + name));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> finLore = new ArrayList<String>();
        for (String line : lore) {
            finLore.add(cc("&7" + line));
        }
        meta.setLore(finLore);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... strings) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        for (String string : strings) {
            lore.add(cc("&7" + string));
        }
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

    private String cc(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ItemStack constructPanel(int i) {
        return (new ItemBuilder(Material.STAINED_GLASS_PANE, i)).name(" ").build();
    }
}