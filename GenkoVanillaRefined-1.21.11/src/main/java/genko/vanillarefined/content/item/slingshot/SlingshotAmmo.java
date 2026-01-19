package genko.vanillarefined.content.item.slingshot;

import genko.vanillarefined.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public final class SlingshotAmmo {
    private SlingshotAmmo() {}

    public static final Map<Item, SlingshotAmmoStats> STATS = Map.of(
            Items.COPPER_NUGGET,  SlingshotAmmoStats.of(3f, 2f/3f),
            Items.IRON_NUGGET,    SlingshotAmmoStats.of(4f, 1f/2f),
            Items.GOLD_NUGGET,    SlingshotAmmoStats.of(5f, 1f/3f),
            Items.AMETHYST_SHARD, SlingshotAmmoStats.of(6f, 2f/3f),
            Items.RESIN_CLUMP, SlingshotAmmoStats.ofStuck(1f, 1f/2f, 5 * 20)
    );

    public static boolean isAmmo(ItemStack stack) {
        return STATS.containsKey(stack.getItem());
    }

    public static SlingshotAmmoStats get(ItemStack stack) {
        return STATS.get(stack.getItem());
    }
}
