package genko.vanillarefined;

import genko.vanillarefined.datagen.ModBlockTagProvider;
import genko.vanillarefined.datagen.ModItemTagProvider;
import genko.vanillarefined.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GenkoDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

			//pack.addProvider(ModBlockTagProvider::new);
			//pack.addProvider(ModItemTagProvider::new);
			pack.addProvider(ModRecipeProvider::new);
	}
}
