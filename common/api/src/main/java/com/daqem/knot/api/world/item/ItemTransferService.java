package com.daqem.knot.api.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ItemTransferService {
    ItemStack insertItem(Level level, BlockPos pos, Direction direction, ItemStack stack);
}