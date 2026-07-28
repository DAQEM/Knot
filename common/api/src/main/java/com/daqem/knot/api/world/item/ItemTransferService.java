package com.daqem.knot.api.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ItemTransferService {

    /**
     * Inserts an ItemStack into a container at the specified position.
     *
     * @param level     The level.
     * @param pos       The position of the container.
     * @param direction The side of the container to insert into.
     * @param stack     The stack to insert.
     * @return The remainder of the stack that could not be inserted.
     */
    ItemStack insertItem(Level level, BlockPos pos, Direction direction, ItemStack stack);
}