package net.zombified_patato.origin_mobs.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zombified_patato.origin_mobs.OriginMobs;


public class ModParticles {

    public static final DefaultParticleType PINK_SMOKE = Registry.register(
            Registry.PARTICLE_TYPE, new Identifier(OriginMobs.MOD_ID, "pink_smoke"), FabricParticleTypes.simple());

    public static void registerModParticles() {
        System.out.println("Registering Mod Particles for " + OriginMobs.MOD_ID);
    }
}
