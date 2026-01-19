package genko.vanillarefined.content.entity;

import genko.vanillarefined.content.item.slingshot.SlingshotAmmo;
import genko.vanillarefined.content.item.slingshot.SlingshotAmmoStats;
import genko.vanillarefined.registry.ModEffects;
import genko.vanillarefined.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SlingshotProjectileEntity extends ThrownItemEntity {

    private boolean inGround = false;
    private int pickupDelay = 7;     // korte delay zodat je hem niet instant oppakt
    private int inGroundAge = 0;
    private static final int MAX_IN_GROUND_AGE = 1200; // ~60 sec (zelfde feeling als arrow)

    public SlingshotProjectileEntity(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
    }

    public SlingshotProjectileEntity(World world, LivingEntity owner, ItemStack ammoStack) {
        super(ModEntities.SLINGSHOT_PROJECTILE, world);

        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());

        ItemStack one = ammoStack.copy();
        one.setCount(1);

        // Dit is de synced stack die gerenderd wordt + die we later teruggeven bij pickup
        this.setItem(one);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET; // fallback
    }

    private SlingshotAmmoStats stats() {
        ItemStack stack = super.getStack(); // tracked stack van ThrownItemEntity
        if (!stack.isEmpty() && SlingshotAmmo.isAmmo(stack)) {
            return SlingshotAmmo.get(stack);
        }
        return SlingshotAmmoStats.of(2f, 1f / 2f);
    }

    @Override
    public void tick() {
        if (inGround) {
            // “vast in de grond”
            this.setVelocity(Vec3d.ZERO);
            this.setNoGravity(true);

            if (pickupDelay > 0) pickupDelay--;

            inGroundAge++;
            if (!this.getEntityWorld().isClient() && inGroundAge >= MAX_IN_GROUND_AGE) {
                this.discard();
                return;
            }

            // baseTick zodat entity lifecycle netjes blijft lopen
            super.baseTick();
            return;
        }

        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult hit) {
        super.onEntityHit(hit);

        if (this.getEntityWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            var owner = this.getOwner();
            SlingshotAmmoStats s = stats();

            hit.getEntity().damage(serverWorld, this.getDamageSources().thrown(this, owner), s.damage());

            if (s.appliesStuck() && hit.getEntity() instanceof LivingEntity living) {
                var stuckeffect = Registries.STATUS_EFFECT.getEntry(ModEffects.STUCK);
                living.addStatusEffect(new StatusEffectInstance(stuckeffect, s.stuckTicks(), 0));
            }

            // entity geraakt => geen pickup, net als “arrow hit mob”
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (this.getEntityWorld().isClient()) return;

        // block geraakt => vast in grond en later op te rapen
        if (hitResult.getType() == HitResult.Type.BLOCK && !inGround) {
            inGround = true;
            inGroundAge = 0;

            BlockHitResult bhr = (BlockHitResult) hitResult;
            Vec3d p = bhr.getPos();

            this.setPosition(p.x, p.y, p.z);
            this.setVelocity(Vec3d.ZERO);
            this.setNoGravity(true);
            return;
        }

        // safety: andere gevallen
        this.discard();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);

        if (this.getEntityWorld().isClient()) return;
        if (!inGround) return;
        if (pickupDelay > 0) return;
        if (player.isSpectator()) return;

        ItemStack ammo = super.getStack().copy();
        if (ammo.isEmpty()) {
            this.discard();
            return;
        }

        ammo.setCount(1);

        boolean inserted = player.getInventory().insertStack(ammo);
        if (!inserted) {
            player.dropItem(ammo, false);
        }

        this.getEntityWorld().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENTITY_ITEM_PICKUP,
                net.minecraft.sound.SoundCategory.PLAYERS,
                0.2F,
                1.0F
        );

        this.discard();
    }
}
