package genko.vanillarefined.registry;

import genko.vanillarefined.Genko;
import genko.vanillarefined.content.entity.SlingshotProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final RegistryKey<EntityType<?>> SLINGSHOT_PROJECTILE_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Genko.MOD_ID, "slingshot_projectile"));

    public static final EntityType<SlingshotProjectileEntity> SLINGSHOT_PROJECTILE =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(Genko.MOD_ID, "slingshot_projectile"),
                    EntityType.Builder.<SlingshotProjectileEntity>create(SlingshotProjectileEntity::new, SpawnGroup.MISC)
                            .dimensions(0.25f, 0.25f)
                            .maxTrackingRange(4)
                            .trackingTickInterval(10)
                            .build(SLINGSHOT_PROJECTILE_KEY)
            );

    public static void register() {
        Genko.LOGGER.info("Registering Mod entities for " + Genko.MOD_ID);
    }
}
