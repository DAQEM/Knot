package com.daqem.knot.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * A unified factory for creating Menu instances.
 * If your menu doesn't use extra data, ignore the buffer.
 */
@FunctionalInterface
public interface MenuConstructor<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory inventory, RegistryFriendlyByteBuf data);
}