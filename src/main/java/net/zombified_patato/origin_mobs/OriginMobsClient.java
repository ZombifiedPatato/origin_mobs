package net.zombified_patato.origin_mobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.zombified_patato.origin_mobs.entity.ModEntities;
import net.zombified_patato.origin_mobs.entity.client.FairyPowderRenderer;
import net.zombified_patato.origin_mobs.networking.EntitySpawnPacket;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class OriginMobsClient implements ClientModInitializer {

    public static final Identifier PACKET_ID = new Identifier(OriginMobs.MOD_ID, "spawn_packet");

    @Override
    public void onInitializeClient(){
        EntityRendererRegistry.register(ModEntities.FAIRY_POWDER, FairyPowderRenderer::new);
        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        System.out.println("running receiveEntityPacket");
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, ((client, handler, byteBuf, responseSender) -> {
            System.out.println("Running for receiver for receiveEntityPacket");
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            client.execute(() -> {
                if (client.world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(client.world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                client.world.addEntity(entityId, e);
            });
        }));
    }

}
