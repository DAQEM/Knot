package com.daqem.knot.fabric.world.item;

import com.daqem.knot.api.world.item.ItemTransferService;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FabricItemTransferService implements ItemTransferService {

    @Override
    public ItemStack insertItem(Level level, BlockPos pos, Direction direction, ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, direction);

        if (storage == null) {
            return stack;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
            transaction.commit();

            if (inserted == stack.getCount()) {
                return ItemStack.EMPTY;
            } else {
                ItemStack remainder = stack.copy();
                remainder.shrink((int) inserted);
                return remainder;
            }
        }
    }
}