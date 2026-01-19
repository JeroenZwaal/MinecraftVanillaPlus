package genko.vanillarefined.registry;

import genko.vanillarefined.Genko;
import genko.vanillarefined.content.item.SlingShotItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {
//    public static final Item SlingShot = registerModItem("slingshot", new Item(new Item.Settings().maxCount(1)));

//    private static Item registerModItem(String path, Item item) {
//        return Registry.register(Registries.ITEM, Identifier.of(Genko.MOD_ID, path), item);
//    }

    public static final Item SlingShot = registerModItem("slingshot", settings -> settings.maxCount(1));

    private static Item registerModItem(String path, java.util.function.UnaryOperator<Item.Settings> settingsOp) {
        Identifier id = Identifier.of(Genko.MOD_ID, path);
        Item.Settings settings = new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id));

        settings = settingsOp.apply(settings);

        return Registry.register(Registries.ITEM, id, new Item(settings));
    }

    public static void registerModItems() {
        Genko.LOGGER.info("Registering Mod items for " + Genko.MOD_ID);
    }
}
