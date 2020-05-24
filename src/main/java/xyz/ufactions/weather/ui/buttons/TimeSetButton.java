package xyz.ufactions.weather.ui.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.ItemBuilder;
import xyz.ufactions.shop.ShopItem;
import xyz.ufactions.weather.WeatherModule;
import xyz.ufactions.weather.ui.TimeSetUI;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class TimeSetButton extends ShopItem<GreenStress> {

    private final WeatherModule module;

    public TimeSetButton(WeatherModule module) {
        super(module.getPlugin(), 16, new ItemBuilder(Material.WATCH).name(C.mHead + "Set Time")
                .lore(C.Italics + "*Click to set the current time*").build());

        this.module = module;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        new TimeSetUI(player, module).openInventory(player);
    }
}