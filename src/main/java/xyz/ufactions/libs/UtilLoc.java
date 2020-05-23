package xyz.ufactions.libs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class UtilLoc {

    public static Vector getMaximumVector(Location l1, Location l2) {
        return Vector.getMaximum(l1.toVector(), l2.toVector());
    }

    public static Vector getMinimumVector(Location l1, Location l2) {
        return Vector.getMinimum(l1.toVector(), l2.toVector());
    }

    public static boolean isInsideOfArea(Location location, Location l1, Location l2) {
        Vector max = getMaximumVector(l1, l2);
        Vector min = getMinimumVector(l1, l2);
        return location.toVector().isInAABB(min, max);
    }

    public static String encodeLoc(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static Location decodeLoc(String string) {
        String[] array = string.split(":");
        return new Location(Bukkit.getWorld(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2]), Double.parseDouble(array[3]), Float.parseFloat(array[4]), Float.parseFloat(array[5]));
    }
}