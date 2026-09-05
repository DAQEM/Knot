package com.daqem.knot.neoforge.world.item;

import com.daqem.knot.api.world.item.ItemTransferService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public class NeoForgeItemTransferService implements ItemTransferService {

    @Override
    public ItemStack insertItem(Level level, BlockPos pos, Direction direction, ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        ResourceHandler<@NotNull ItemResource> handler = level.getCapability(Capabilities.Item.BLOCK, pos, direction);

        if (handler == null) {
            return stack;
        }

        ItemResource resource = ItemResource.of(stack);

        try (Transaction tx = Transaction.open(null)) {
            long inserted = handler.insert(resource, stack.getCount(), tx);

            if (inserted > 0) {
                tx.commit();
            }

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