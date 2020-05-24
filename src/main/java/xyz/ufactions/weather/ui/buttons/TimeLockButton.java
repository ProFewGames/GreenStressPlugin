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
import xyz.ufactions.weather.WeatherOptions;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class TimeLockButton extends ShopItem<GreenStress> {

	private final WeatherModule module;

	public TimeLockButton(Player player, WeatherModule module) {
		super(module.getPlugin(), 14, new ItemBuilder(Material.WATCH).name(
				(module.getWeather(player.getWorld()).isTimeLock() ? C.cGreen + "Unlock" : C.cRed + "Lock") + " Time")
				.lore(C.Italics + "*Click to lock current time*").build());

		this.module = module;
	}

	@Override
	public void onClick(Player player, ClickType clickType) {
		player.closeInventory();
		WeatherOptions weather = module.getWeather(player.getWorld());
		boolean locked = !weather.isTimeLock();
		long lockedTime = player.getWorld().getTime();
		if (locked) {
			weather.setLockedTime(lockedTime);
		} else {
			weather.setLockedTime(0L);
		}
		weather.setTimeLock(locked);
		player.sendMessage(F.main("GreenBull", "Time has been " + (weather.isTimeLock()
				? C.cRed + "locked " + C.mBody + "at "
				+ F.elem(lockedTime % 24000L + " Ticks")
				: C.cGreen + "unlocked") + "."));
	}
}