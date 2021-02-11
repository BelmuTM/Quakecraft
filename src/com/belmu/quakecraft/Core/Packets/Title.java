package com.belmu.quakecraft.Core.Packets;

import com.belmu.quakecraft.Quake;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Belmu (https://github.com/BelmuTM/)
 */
public class Title {

    public final Quake plugin;
    public Title(Quake plugin) {
        this.plugin = plugin;
    }

    private String[] winnerTitles = {
            "§6VICTORY",
            "§eV§6ICTORY",
            "§fV§eI§6CTORY",
            "§eV§fI§eC§6TORY",
            "§6V§eI§fC§eT§6ORY",
            "§6VI§eC§fT§eO§6RY",
            "§6VIC§eT§fO§eR§6Y",
            "§6VICT§eO§fR§eY",
            "§6VICTO§eR§fY",
            "§6VICTOR§eY",
    };

    private String[] loserTitles = {
            "§4DEFEAT",
            "§cDEFEAT"
    };

    public void sendTitle(Player player, String text, ChatColor color, int fadeIn, int time, int fadeOut) {

        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, time, fadeOut);
        IChatBaseComponent titleText = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleText);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    public void sendSubTitle(Player player, String text, ChatColor color, int fadeIn, int time, int fadeOut) {

        PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, time, fadeOut);
        IChatBaseComponent subTitleText = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitleText);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    private Map<Player, Integer> frames = new HashMap<>();
    public void winTitle(Player player, boolean winner) {
        frames.put(player, 0);

        new BukkitRunnable() {
            String[] msgs;

            @Override
            public void run() {

                if(winner) msgs = winnerTitles;
                if(!winner) msgs = loserTitles;

                String message = "";

                for (String msg : msgs) {
                    if (frames.get(player) == Arrays.asList(msgs).indexOf(msg))
                        message = msg;
                }
                frames.put(player, frames.get(player) + 1);
                if (frames.get(player) >= (msgs.length - 1)) {
                    frames.put(player, 0);
                    this.cancel();
                }

                if(Bukkit.getOnlinePlayers().size() == 0) return;
                sendTitle(player, message, ChatColor.BLACK, 0, 65, 0);
            }
        }.runTaskTimer(plugin, 12, 12);
    }
}
