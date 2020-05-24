package xyz.ufactions.weather.ui.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.F;
import xyz.ufactions.libs.ItemBuilder;
import xyz.ufactions.shop.ShopItem;
import xyz.ufactions.weather.WeatherModule;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class ToggleRainButton extends ShopItem<GreenStress> {

	private final WeatherModule module;

    public ToggleRainButton(WeatherModule module) {
        super(module.getPlugin(), 12, new ItemBuilder(Material.WATER_BUCKET).name(C.mHead + "Toggle Rain")
                .lore(C.Italics + "*Click to toggle rain*").build());

        this.module = module;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        player.closeInventory();
        boolean storm = player.getWorld().hasStorm();
        if (!storm) {
            if (module.getWeather(player.getWorld()).isDisableRain()) {
                player.sendMessage(F.error("GreenBull", "Unable to toggle rain on because natural rain has been disabled."));
                return;
            }
        }
        player.getWorld().setStorm(!storm);
        player.sendMessage(F.main("GreenBull", "Toggled rain " + F.oo(!storm) + "."));
    }
}