package com.daqem.knot.test.menu;

import com.daqem.knot.test.registry.TestMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BoxOfSecretsMenu extends AbstractContainerMenu {

    private final BlockPos pos;

    // Called directly by the Block. We pass the data raw.
    public BoxOfSecretsMenu(int syncId, Inventory inventory, BlockPos pos) {
        super(TestMenus.BOX_OF_SECRETS.get(), syncId);
        this.pos = pos;
        this.initSlots(inventory);
    }

    // Called by Knot/Minecraft when the packet arrives. We read data from the buffer.
    public BoxOfSecretsMenu(int syncId, Inventory inventory, FriendlyByteBuf data) {
        this(syncId, inventory, data.readBlockPos());
    }

    private void initSlots(Inventory inventory) {
        // Add player inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) { return ItemStack.EMPTY; }

    @Override
    public boolean stillValid(@NotNull Player player) { return true; }
}