package net.zombified_patato.origin_mobs.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zombified_patato.origin_mobs.OriginMobs;

public class ModItemGroups {
    public static final ItemGroup ORIGIN_MOBS = FabricItemGroupBuilder.build(new Identifier(OriginMobs.MOD_ID, "origin_mobs"),
            () -> new ItemStack(ModItems.FAIRY_POWDER));
}
