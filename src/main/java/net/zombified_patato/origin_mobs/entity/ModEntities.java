package net.zombified_patato.origin_mobs.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zombified_patato.origin_mobs.OriginMobs;
import net.zombified_patato.origin_mobs.entity.custom.FairyPowderEntity;

public class ModEntities {

    public static final EntityType<FairyPowderEntity> FAIRY_POWDER= Registry.register(
            Registry.ENTITY_TYPE, new Identifier(OriginMobs.MOD_ID, "fairy_powder"),
            FabricEntityTypeBuilder.<FairyPowderEntity>create(SpawnGroup.MISC, FairyPowderEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build()
    );

    public static void registerModEntities() {
        System.out.println("Registering Mod Entities for " + OriginMobs.MOD_ID);
    }

}
