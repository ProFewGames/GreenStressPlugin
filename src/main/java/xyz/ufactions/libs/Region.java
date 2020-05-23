package xyz.ufactions.libs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import xyz.ufactions.libs.UtilLoc;

import java.util.ArrayList;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class Region {

    private final BlockVector pos1;
    private final BlockVector pos2;

    public Region(BlockVector pos1, BlockVector pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> list = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isInside(player.getLocation())) {
                list.add(player);
            }
        }
        return list;
    }

    public boolean isInside(Location location) {
        return UtilLoc.isInsideOfArea(location, pos1.toLocation(location.getWorld()), pos2.toLocation(location.getWorld()));
    }

    public BlockVector getCenter() {
        return getMaximumPoint().midpoint(getMinimumPoint()).toBlockVector();
    }

    public BlockVector getMaximumPoint() {
        return BlockVector.getMaximum(pos1, pos2).toBlockVector();
    }

    public BlockVector getMinimumPoint() {
        return BlockVector.getMinimum(pos1, pos2).toBlockVector();
    }

    public BlockVector getPos1() {
        return pos1;
    }

    public BlockVector getPos2() {
        return pos2;
    }

    public String serialize() {
        return pos1.getX() + "," +
                pos1.getY() + "," +
                pos1.getZ() + "," +
                pos2.getX() + "," +
                pos2.getY() + "," +
                pos2.getZ();
    }

    public static Region deserialize(String serialized) {
        String[] array = serialized.split(",");
        BlockVector pos1 = new BlockVector(Double.parseDouble(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2]));
        BlockVector pos2 = new BlockVector(Double.parseDouble(array[3]), Double.parseDouble(array[4]), Double.parseDouble(array[5]));
        return new Region(pos1, pos2);
    }
}