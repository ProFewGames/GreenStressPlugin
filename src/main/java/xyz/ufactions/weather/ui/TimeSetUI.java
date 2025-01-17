package xyz.ufactions.weather.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.ufactions.gsp.GreenStress;
import xyz.ufactions.libs.C;
import xyz.ufactions.libs.F;
import xyz.ufactions.libs.ItemBuilder;
import xyz.ufactions.shop.Shop;
import xyz.ufactions.shop.ShopItem;
import xyz.ufactions.weather.WeatherModule;
import xyz.ufactions.weather.WeatherOptions;

import java.util.Arrays;
import java.util.Collections;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class TimeSetUI extends Shop {

    private boolean a = false;
    private final WeatherModule wm;

    public TimeSetUI(Player player, WeatherModule module) {
        super(module.getPlugin(), new WeatherUI(player, module), C.cGold + C.Bold + "Time Setter", 27, ShopFiller.NONE,
                CreatePreset(module, 20, Material.WATCH, C.cGold + C.Bold + "Day Preset", true, 0L),
                CreatePreset(module, 22, Material.WATCH, C.cGold + C.Bold + "Night Preset", true, 14000L),
                CreatePreset(module, 24, Material.WATCH, C.cGold + C.Bold + "Dawn Preset", true, 23000L),
                CreatePreset(module, 10, Material.REDSTONE_TORCH_ON, C.mBody + "50", false, 50L),
                CreatePreset(module, 12, Material.REDSTONE_TORCH_ON, C.mBody + "500", false, 500L),
                CreatePreset(module, 14, Material.REDSTONE_TORCH_ON, C.mBody + "5000", false, 5000L),
                CreatePreset(module, 16, Material.REDSTONE_TORCH_ON, C.mBody + "10000", false, 10000L),
                CreatePreset(module, 0, Material.REDSTONE_TORCH_ON, C.mBody + "10", false, 10L),
                CreatePreset(module, 2, Material.REDSTONE_TORCH_ON, C.mBody + "100", false, 100L),
                CreatePreset(module, 4, Material.REDSTONE_TORCH_ON, C.mBody + "1000", false, 1000L),
                CreatePreset(module, 6, Material.REDSTONE_TORCH_ON, C.mBody + "2000", false, 2000L),
                CreatePreset(module, 8, Material.REDSTONE_TORCH_ON, C.mBody + "3000", false, 3000L));
        wm = module;
    }

    @Override
    public void onInventoryOpen(Player player) {
        WeatherOptions weather = wm.getWeather(player.getWorld());
        if (weather.isTimeLock()) {
            a = true;
            weather.setTimeLock(false);
            player.sendMessage(F.main("GreenBull", "Time lock turned " + F.oo(false) + "."));
        }
    }

    @Override
    public void onClose(Player player) {
        player.sendMessage(F.main("GreenBull", "Time set to " + F.elem(player.getWorld().getTime() % 24000L + " Ticks") + "."));
        if (a) {
            WeatherOptions weather = wm.getWeather(player.getWorld());
            weather.setLockedTime(player.getWorld().getTime());
            weather.setTimeLock(true);
            player.sendMessage(F.main("GreenBull", "Time lock turned " + F.oo(true) + "."));
        }
        wm.save();
    }

    private static ShopItem<GreenStress> CreatePreset(WeatherModule module, int position, Material material,
                                                      String displayName, final boolean isPreset, final long time) {
        return new ShopItem<GreenStress>(module.getPlugin(), position, new ItemBuilder(material).name(displayName).lore(isPreset
                ? Collections.singletonList(C.Italics + "*Time Preset*")
                : Arrays.asList("Left click to add " + time + " Ticks", "or right click to remove", time + " Ticks"))
                .build()) {

            @Override
            public void onClick(Player player, ClickType clickType) {
                if (isPreset) {
                    player.getWorld().setTime(time);
                    player.closeInventory();
                } else {
                    if (clickType == ClickType.LEFT) {
                        player.getWorld().setTime(player.getWorld().getTime() + Math.abs(time));
                    } else if (clickType == ClickType.RIGHT) {
                        player.getWorld().setTime(player.getWorld().getTime() - Math.abs(time));
                    }
                    player.sendMessage(F.main("GreenBull", "New time: "
                            + F.elem(player.getWorld().getTime() % 24000L + " Ticks")));
                }
            }
        };
    }
}