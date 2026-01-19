package genko.vanillarefined.client;

import genko.vanillarefined.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class GenkoClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(ModEntities.SLINGSHOT_PROJECTILE, FlyingItemEntityRenderer::new);

	}
}
