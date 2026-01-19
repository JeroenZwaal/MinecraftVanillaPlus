package genko.vanillarefined.registry;

import genko.vanillarefined.Genko;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SlingShot = registerModItem("slingshot", new Item(new Item.Settings().maxCount(1)));

    private static Item registerModItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Genko.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Genko.LOGGER.info("Registering Mod items for " + Genko.MOD_ID);
    }
}
