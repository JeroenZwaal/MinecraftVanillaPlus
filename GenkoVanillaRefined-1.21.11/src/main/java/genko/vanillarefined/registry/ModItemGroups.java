package genko.vanillarefined.registry;

import genko.vanillarefined.Genko;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public final class ModItemGroups {

    private ModItemGroups() {}

    public static void registerItemGroups() {
        Genko.LOGGER.info("Registering creative tab entries for " + Genko.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ModItems.SLINGSHOT);
        });
    }
}
