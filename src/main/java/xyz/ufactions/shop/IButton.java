package xyz.ufactions.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public interface IButton {

	/**
	 * @return The ItemStack that will show up in the shop, use this method in
	 *         your button class to add an updater.
	 */
	ItemStack getItem();

	void onClick(Player player, ClickType clickType);
}