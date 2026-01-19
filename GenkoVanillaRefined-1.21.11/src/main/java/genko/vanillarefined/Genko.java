package genko.vanillarefined;

import genko.vanillarefined.registry.ModItemGroups;
import genko.vanillarefined.registry.ModItems;
import genko.vanillarefined.registry.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Genko implements ModInitializer {
	public static final String MOD_ID = "vanillarefined";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();

		ModScreenHandlers.register();

		// Register a callback to log when a player uses a fletching table on the server
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClient()) {
				BlockPos pos = hitResult.getBlockPos();
				Block block = world.getBlockState(pos).getBlock();
				if (block == Blocks.FLETCHING_TABLE) {
					LOGGER.info("Fletching table used at {} by {}", pos, player.getName().getString());
					// Open the vanilla crafting table screen when using a fletching table
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
						(syncId, inv, p) -> new CraftingScreenHandler(syncId, (PlayerInventory) inv),
						Text.translatable("container.crafting")
					));
//					player.openHandledScreen(
//					    new SimpleNamedScreenHandlerFactory(
//					        (syncId, inv, p) -> new FletchingTableScreenHandler(syncId, inv),
//					        Text.translatable("container.vanillarefined.fletching")
//					    )
//					);
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});
	}
}