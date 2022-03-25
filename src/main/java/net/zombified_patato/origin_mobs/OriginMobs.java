package net.zombified_patato.origin_mobs;

import net.fabricmc.api.ModInitializer;
import net.zombified_patato.origin_mobs.block.ModBlocks;
import net.zombified_patato.origin_mobs.entity.ModEntities;
import net.zombified_patato.origin_mobs.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OriginMobs implements ModInitializer {

	public static final String MOD_ID = "origin_mobs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerBlocks();
		ModEntities.registerModEntities();
	}
}
