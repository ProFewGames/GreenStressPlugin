package xyz.ufactions.libs;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class ColorLib {

    public static Material cp(ChatColor color) {
        if(color==ChatColor.WHITE) return Material.WHITE_STAINED_GLASS_PANE;
        else if(color==ChatColor.GOLD) return Material.ORANGE_STAINED_GLASS_PANE;
        // MAGENTA
        else if(color==ChatColor.BLUE) return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
        else if(color==ChatColor.YELLOW) return Material.YELLOW_STAINED_GLASS_PANE;
        else if(color==ChatColor.GREEN) return Material.LIME_STAINED_GLASS_PANE;
        else if(color==ChatColor.LIGHT_PURPLE) return Material.PINK_STAINED_GLASS_PANE;
        else if(color==ChatColor.DARK_GRAY) return Material.GRAY_STAINED_GLASS_PANE;
        else if(color==ChatColor.GRAY) return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
        else if(color==ChatColor.AQUA) return Material.CYAN_STAINED_GLASS_PANE;
        else if(color==ChatColor.DARK_PURPLE) return Material.PURPLE_STAINED_GLASS_PANE;
        else if(color==ChatColor.DARK_BLUE) return Material.BLUE_STAINED_GLASS_PANE;
        // BROWN
        else if(color==ChatColor.DARK_GREEN) return Material.GREEN_STAINED_GLASS_PANE;
        else if(color==ChatColor.RED) return Material.RED_STAINED_GLASS_PANE;
        else if(color==ChatColor.BLACK) return Material.BLACK_STAINED_GLASS_PANE;
        return Material.MAGENTA_STAINED_GLASS_PANE;
    }

    public static Material cw(ChatColor color) {
        if (color == ChatColor.WHITE) return Material.WHITE_WOOL;
        else if (color == ChatColor.GOLD) return Material.ORANGE_WOOL;
        else if (color == ChatColor.AQUA) return Material.LIGHT_BLUE_WOOL;
        else if (color == ChatColor.YELLOW) return Material.YELLOW_WOOL;
        else if (color == ChatColor.GREEN) return Material.LIME_WOOL;
        else if (color == ChatColor.LIGHT_PURPLE) return Material.PINK_WOOL;
        else if (color == ChatColor.DARK_GRAY) return Material.GRAY_WOOL;
        else if (color == ChatColor.GRAY) return Material.LIGHT_GRAY_WOOL;
        else if (color == ChatColor.BLUE) return Material.CYAN_WOOL;
        else if (color == ChatColor.DARK_PURPLE) return Material.PURPLE_WOOL;
        else if (color == ChatColor.DARK_BLUE) return Material.BLUE_WOOL;
        else if (color == ChatColor.DARK_GREEN) return Material.GREEN_WOOL;
        else if (color == ChatColor.RED) return Material.RED_WOOL;
        else if (color == ChatColor.BLACK) return Material.BLACK_WOOL;
        return Material.BROWN_WOOL;
    }
}