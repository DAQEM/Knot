package com.daqem.knot.registry.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * A unified factory for creating Menu instances with extra data.
 */
@FunctionalInterface
public interface MenuConstructor<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory inventory, RegistryFriendlyByteBuf data);
}