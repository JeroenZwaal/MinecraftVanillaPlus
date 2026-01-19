package genko.vanillarefined.registry;

import genko.vanillarefined.Genko;
import genko.vanillarefined.content.effect.StuckStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect STUCK = Registry.register(
            Registries.STATUS_EFFECT,
            Identifier.of(Genko.MOD_ID, "stuck"),
            new StuckStatusEffect()
    );

    public static void register() {
        Genko.LOGGER.info("Registering Mod effects for " + Genko.MOD_ID);
    }
}
