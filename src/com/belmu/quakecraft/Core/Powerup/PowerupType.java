package com.belmu.quakecraft.Core.Powerup;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Belmu (https://github.com/BelmuTM/)
 */
public enum PowerupType {

    SPEED(EnumParticle.FIREWORKS_SPARK, Material.FEATHER, 50),
    RAPID_FIRE(EnumParticle.FLAME, Material.BLAZE_POWDER, 5.5);

    private EnumParticle particle;
    private Material material;
    private double time;

    PowerupType(EnumParticle particle, Material material, double time) {
        this.particle = particle;
        this.material = material;

        this.time = time;
    }

    public EnumParticle getParticle() { return particle; }
    public Material getMaterial() { return material; }
    public double getTime() { return time; }
}
