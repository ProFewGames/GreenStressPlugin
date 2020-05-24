package xyz.ufactions.weather.ui;

import org.bukkit.entity.Player;

import xyz.ufactions.libs.C;
import xyz.ufactions.shop.Shop;
import xyz.ufactions.weather.WeatherModule;
import xyz.ufactions.weather.ui.buttons.NoRainButton;
import xyz.ufactions.weather.ui.buttons.TimeLockButton;
import xyz.ufactions.weather.ui.buttons.TimeSetButton;
import xyz.ufactions.weather.ui.buttons.ToggleRainButton;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class WeatherUI extends Shop {

	public WeatherUI(Player player, WeatherModule module) {
		super(module.getPlugin(), C.mHead + "Weather UI " + C.cGray + C.Italics + player.getWorld().getName(), 27,
				ShopFiller.PANE, new NoRainButton(player, module), new ToggleRainButton(module),
				new TimeLockButton(player, module), new TimeSetButton(module));
	}
}