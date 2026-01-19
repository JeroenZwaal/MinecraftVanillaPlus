package genko.vanillarefined.mixin;

import genko.vanillarefined.screenhandler.FletchingTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingTableBlock.class)
public class FletchingTableBlockMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void openScreen(
            BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir
    ) {
        if (!world.isClient()) {
            player.openHandledScreen(
                new SimpleNamedScreenHandlerFactory(
                        (syncId, inv, p) -> new FletchingTableScreenHandler(syncId, inv),
                        Text.translatable("container.vanillarefined.fletching")
                )
            );
        }
        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
