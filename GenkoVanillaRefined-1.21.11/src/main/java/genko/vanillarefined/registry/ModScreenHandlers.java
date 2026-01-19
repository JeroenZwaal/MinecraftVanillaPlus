package genko.vanillarefined.registry;

import genko.vanillarefined.screenhandler.FletchingTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;


public class ModScreenHandlers {

    public static ScreenHandlerType<FletchingTableScreenHandler> FLETCHING_TABLE;

    public static void register() {

        FLETCHING_TABLE = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of("vanillarefined", "fletching_table"),
            new ScreenHandlerType<FletchingTableScreenHandler>(FletchingTableScreenHandler::new, null)
        );

    }
}
