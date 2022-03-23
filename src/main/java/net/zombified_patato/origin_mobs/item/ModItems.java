package net.zombified_patato.origin_mobs.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zombified_patato.origin_mobs.OriginMobs;

public class ModItems {

    public static final Item FAIRY_POWDER = registerItem("fairy_powder",
            new Item(new FabricItemSettings().group(ModItemGroups.ORIGIN_MOBS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(OriginMobs.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Mod Items for " + OriginMobs.MOD_ID);
    }

}
