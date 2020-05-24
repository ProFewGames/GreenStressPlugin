package xyz.ufactions.weather.ui.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.F;
import xyz.ufactions.shop.ShopItem;
import xyz.ufactions.weather.WeatherModule;
import xyz.ufactions.weather.WeatherOptions;

import java.util.Collections;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class NoRainButton extends ShopItem<GreenStress> {

    private final WeatherModule module;

    public NoRainButton(Player player, WeatherModule module) {
        super(module.getPlugin(), 10, a(player, module));

        this.module = module;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        player.closeInventory();
        WeatherOptions weather = module.getWeather(player.getWorld());
        weather.setDisableRain(!weather.isDisableRain());
        player.sendMessage(F.main("GreenBull", "Natural rain has been turned " + F.oo(!weather.isDisableRain()) + "."));
    }

    private static ItemStack a(Player player, WeatherModule module) {
        WeatherOptions weather = module.getWeather(player.getWorld());
        boolean a = weather.isDisableRain();
        ItemStack item = new ItemStack(Material.WATER_BUCKET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((a ? C.cGreen + "Enable" : C.cRed + "Disable") + " Natural Rain");
        meta.setLore(Collections.singletonList(C.cGray + C.Italics + "*Click to enable/disable natural rain*"));
        item.setItemMeta(meta);
        return item;
    }
}