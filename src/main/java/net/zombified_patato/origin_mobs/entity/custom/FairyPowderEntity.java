package net.zombified_patato.origin_mobs.entity.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.zombified_patato.origin_mobs.OriginMobsClient;
import net.zombified_patato.origin_mobs.entity.ModEntities;
import net.zombified_patato.origin_mobs.item.ModItems;
import net.zombified_patato.origin_mobs.networking.EntitySpawnPacket;

import java.util.List;

public class FairyPowderEntity extends ThrownItemEntity {

    public FairyPowderEntity(EntityType<? extends FairyPowderEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    public FairyPowderEntity(World world, LivingEntity owner) {
        super(ModEntities.FAIRY_POWDER, owner, world);
        this.ignoreCameraFrustum = true;
    }

    public FairyPowderEntity(World world, double x, double y, double z) {
        super(ModEntities.FAIRY_POWDER, x, y, z, world);
        this.ignoreCameraFrustum = true;
    }

    @Override
    public Item getDefaultItem() {
        return ModItems.FAIRY_POWDER;
    }

    @Override
    protected float getGravity() {
        return 0.04f;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            float radius = 1.0f;
            ParticleEffect particleEffect = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            for (int j = 0; j < 16; ++j) {
                double velocityZ;
                double velocityY;
                double velocityX;
                float h = this.random.nextFloat() * ((float)Math.PI * 2);
                float k = MathHelper.sqrt(this.random.nextFloat()) * radius;
                double posX = this.getX() + (double)(MathHelper.cos(h) * k);
                double posY = this.getY();
                double posZ = this.getZ() + (double)(MathHelper.sin(h) * k);
                velocityX = (0.5 - this.random.nextDouble()) * 0.15;
                velocityY = 0.01f;
                velocityZ = (0.5 - this.random.nextDouble()) * 0.15;
                this.world.addImportantParticle(particleEffect, posX, posY, posZ, velocityX, velocityY, velocityZ);
            }
        }
    }


    protected void onCollision(HitResult hitResult){
        super.onCollision(hitResult);
        if (this.world.isClient) {
            return;
        }
        effectEntities();
        this.world.sendEntityStatus(this,(byte)3);
        this.kill();
    }

    private void effectEntities() {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            for (LivingEntity livingEntity : list) {
                double distance;
                if (!((distance = this.squaredDistanceTo(livingEntity)) < 32.0)) continue;
                double timeMultiplier = 1.0 - Math.sqrt(distance) / 4.0;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,
                        (int)(timeMultiplier * 600 + 0.5)));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION,
                        (int)(timeMultiplier * 400 + 0.5)));
                }
            }
        }

    @Override
    public Packet<?> createSpawnPacket() {
        System.out.println("Creating spawn packet for fairy powder!");
        return EntitySpawnPacket.create(this, OriginMobsClient.PACKET_ID);
    }
}