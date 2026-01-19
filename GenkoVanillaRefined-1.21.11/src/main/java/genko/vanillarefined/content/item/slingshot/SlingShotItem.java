package genko.vanillarefined.content.item.slingshot;

import genko.vanillarefined.content.entity.SlingshotProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SlingShotItem extends Item {

    // 0.6s * 20 ticks = 12
    private static final int CHARGE_TICKS = 12;
    private static final float BASE_SPEED = 2.0f;

    public SlingShotItem(Settings settings) {
        super(settings);
    }

    public static float getPullProgress(Object entityObj, ItemStack stack) {
        if (!(entityObj instanceof LivingEntity living)) return 0.0f;
        if (living.getActiveItem() != stack) return 0.0f;

        int used = 7200 - living.getItemUseTimeLeft();
        float pull = used / (float) CHARGE_TICKS;
        return MathHelper.clamp(pull, 0.0f, 1.0f);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 7200;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack weaponStack = user.getStackInHand(hand);

        // Check ammo voordat we starten met chargen
        ItemStack ammo = findAmmo(user);
        if (ammo.isEmpty() && !user.getAbilities().creativeMode) {
            return ActionResult.FAIL;
        }

        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    @Override
    public boolean onStoppedUsing(ItemStack weaponStack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return false;

        int usedTicks = getMaxUseTime(weaponStack, user) - remainingUseTicks;
        if (usedTicks < CHARGE_TICKS) return false;

        ItemStack ammo = findAmmo(player);
        if (ammo.isEmpty() && !player.getAbilities().creativeMode) return false;

        if (!world.isClient()) {
            ItemStack ammoOne = ammo.isEmpty() ? ItemStack.EMPTY : ammo.copy();
            if (!ammoOne.isEmpty()) ammoOne.setCount(1);

            SlingshotAmmoStats stats = ammoOne.isEmpty()
                    ? SlingshotAmmoStats.of(2f, 1f/2f)
                    : SlingshotAmmo.get(ammoOne);

            SlingshotProjectileEntity proj = new SlingshotProjectileEntity(world, player, ammoOne);

            float speed = BASE_SPEED * stats.rangeMultiplier();
            proj.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, speed, 1.5f);

            world.spawnEntity(proj);

            weaponStack.damage(1, player, player.getActiveHand());

            if (!player.getAbilities().creativeMode && !ammo.isEmpty()) {
                ammo.decrement(1);
            }
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 1.0f, 1.4f);

        return true;
    }

    private static ItemStack findAmmo(PlayerEntity player) {
        if (SlingshotAmmo.isAmmo(player.getMainHandStack())) return player.getMainHandStack();
        if (SlingshotAmmo.isAmmo(player.getOffHandStack())) return player.getOffHandStack();

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack s = player.getInventory().getStack(i);
            if (SlingshotAmmo.isAmmo(s)) return s;
        }
        return ItemStack.EMPTY;
    }
}
