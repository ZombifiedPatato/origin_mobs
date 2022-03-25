package net.zombified_patato.origin_mobs.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
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

    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        effectEntities();
    }

    protected void onCollision(HitResult hitResult){
        super.onCollision(hitResult);
        if (this.world.isClient) {
            return;
        }
        effectEntities();

    }

    private void effectEntities() {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        if (!list.isEmpty()) {
            Entity entity2 = this.getEffectCause();
            for (LivingEntity livingEntity : list) {
                double distance;
                if (!((distance = this.squaredDistanceTo(livingEntity)) < 16.0)) continue;
                double timeMultiplier = 1.0 - Math.sqrt(distance) / 4.0;
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,
                        (int)(timeMultiplier * 500 + 0.5)));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION,
                        (int)(timeMultiplier * 250 + 0.5)));
                }
            }
        }

    @Override
    public Packet<?> createSpawnPacket() {
        System.out.println("Creating spawn packet for fairy powder!");
        return EntitySpawnPacket.create(this, OriginMobsClient.PACKET_ID);
    }
}