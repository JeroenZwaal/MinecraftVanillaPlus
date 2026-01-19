package genko.vanillarefined.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class StuckStatusEffect extends StatusEffect {

    private static final UUID MOVEMENT_SPEED_UUID =
            UUID.fromString("b1b4a2e8-7c7c-4e9a-9a3a-7b2b2e1e0f10");

    public StuckStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x7A5CFF);

        // 1.21.x: use ADD_MULTIPLIED_TOTAL instead of MULTIPLY_TOTAL
        this.addAttributeModifier(
                EntityAttributes.MOVEMENT_SPEED,
                Identifier.of(MOVEMENT_SPEED_UUID.toString()),
                -1.0,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // run every tick
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.setSprinting(false);

        Vec3d v = entity.getVelocity();

        // Jump cancellen
        if (v.y > 0) {
            entity.setVelocity(v.x, 0, v.z);
        }

        // Sliding beperken
        if (entity.isOnGround()) {
            entity.setVelocity(0, entity.getVelocity().y, 0);
        }

        entity.velocityDirty = true;
        return true;
    }
}
