package genko.vanillarefined.registry;
import genko.vanillarefined.Genko;
import genko.vanillarefined.content.item.slingshot.SlingShotItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SLINGSHOT = registerModItem("slingshot",settings -> new SlingShotItem(settings.maxCount(1).maxDamage(128)));


    private static Item registerModItem(String path, java.util.function.Function<Item.Settings, Item> factory) {
        Identifier id = Identifier.of(Genko.MOD_ID, path);

        Item.Settings settings = new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id));

        Item item = factory.apply(settings);
        return Registry.register(Registries.ITEM, id, item);
    }



    public static void registerModItems() {
        Genko.LOGGER.info("Registering Mod items for " + Genko.MOD_ID);
    }
}
