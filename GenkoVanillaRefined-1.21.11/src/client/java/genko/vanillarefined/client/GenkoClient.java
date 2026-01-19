package genko.vanillarefined.client;

import genko.vanillarefined.registry.ModScreenHandlers;
import genko.vanillarefined.screen.FletchingTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class GenkoClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HandledScreens.register(
				ModScreenHandlers.FLETCHING_TABLE,
				FletchingTableScreen::new
		);
	}
}