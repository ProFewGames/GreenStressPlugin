package xyz.ufactions.libs;

import net.minecraft.server.v1_15_R1.ChatMessage;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class TitleAPI {

    public static void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeout) {
        setTimings(player, fadeIn, stay, fadeout);
        sendPacket(player, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatMessage(title)),
                new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatMessage(subtitle)));
    }

    @Deprecated
    public static void sendTabHF(String header, String footer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            // TODO
        }
    }


    @Deprecated
    public static void sendTabHF(Player player, String header, String footer) {
        // TODO
    }


    public static void sendActionBar(String message, int stay) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendActionBar(player, message, stay);
        }
    }

    public static void sendActionBar(Player p, String message, int stay) {
        setTimings(p, 0, stay, 0);
        sendPacket(p, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, new ChatMessage(message)));
    }

    private static void setTimings(Player player, int fadeIn, int stay, int fadeOut) {
        sendPacket(player, new PacketPlayOutTitle(fadeIn, stay, fadeOut));
    }

    private static void sendPacket(Player player, Packet<?>... packets) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        for (Packet<?> packet : packets) {
            connection.sendPacket(packet);
        }
    }
}
