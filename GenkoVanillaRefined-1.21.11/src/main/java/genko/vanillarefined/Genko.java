package genko.vanillarefined;

import genko.vanillarefined.registry.ModEffects;
import genko.vanillarefined.registry.ModEntities;
import genko.vanillarefined.registry.ModItemGroups;
import genko.vanillarefined.registry.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Genko implements ModInitializer {
	public static final String MOD_ID = "vanillarefined";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModEntities.register();
		ModEffects.register();
	}
}