package com.belmu.quake.Core.Map;

import com.belmu.quake.Quake;
import com.belmu.quake.Utils.Countdown;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Belmu (https://github.com/BelmuTM/)
 */
public class SpawnProtection {

    static List<UUID> invulnerable = new ArrayList<>();

    public final Quake plugin;
    public SpawnProtection(Quake plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param time Defines the amount of time when the player will be invulnerable.
     */
    public void invulnerability(Player player, double time) {
        UUID uuid = player.getUniqueId();

        Countdown invulnerability = new Countdown(plugin,
            time,

            () -> {
                if(!invulnerable.contains(uuid)) invulnerable.add(uuid);

                for(Player online : Bukkit.getOnlinePlayers()) {
                    online.hidePlayer(player);
                    ((CraftPlayer) online).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle()));
                }
            },
            () -> {
                invulnerable.remove(uuid);
                for(Player online : Bukkit.getOnlinePlayers()) online.showPlayer(player);
            },
            (t) -> {}
        );
        invulnerability.scheduleTimer();
    }

    public static boolean isInvulnerable(Player player) {
        return invulnerable.contains(player.getUniqueId());
    }
}