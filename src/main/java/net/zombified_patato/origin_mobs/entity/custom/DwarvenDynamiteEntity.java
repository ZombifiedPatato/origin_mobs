package net.zombified_patato.origin_mobs.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.zombified_patato.origin_mobs.OriginMobsClient;
import net.zombified_patato.origin_mobs.networking.EntitySpawnPacket;
import org.jetbrains.annotations.Nullable;

public class DwarvenDynamiteEntity extends TntEntity {


    public DwarvenDynamiteEntity(EntityType<? extends DwarvenDynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DwarvenDynamiteEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        super(world, x, y, z, igniter);
        this.setFuse(140);
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }

        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.world.isClient) {
                this.explode();
            }
        } else {
            this.updateWaterState();
            if (this.world.isClient) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void explode() {
        float f = 6.0F;
        this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), 4.0F, Explosion.DestructionType.BREAK);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        System.out.println("Creating spawn packet for dwarven dynamite!");
        return EntitySpawnPacket.create(this, OriginMobsClient.SPAWN_PACKET_ID);
    }
}
