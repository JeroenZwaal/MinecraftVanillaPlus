package genko.vanillarefined.screenhandler;

import genko.vanillarefined.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FletchingTableScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    public FletchingTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.FLETCHING_TABLE, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        this.inventory.onOpen(playerInventory.player);

        // Table slots (example positions, adjust in the screen if needed)
        this.addSlot(new Slot(inventory, 0, 20, 18));
        this.addSlot(new Slot(inventory, 1, 38, 18));
        this.addSlot(new Slot(inventory, 2, 56, 18));

        // Player inventory (3 rows of 9)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 51 + row * 18));
            }
        }
        // Hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 109));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            result = stack.copy();

            int containerSize = this.inventory.size();
            int slotCount = this.slots.size();

            if (index < containerSize) {
                // from container to player inventory
                if (!this.insertItem(stack, containerSize, slotCount, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // from player inventory to container
                if (!this.insertItem(stack, 0, containerSize, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return result;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
