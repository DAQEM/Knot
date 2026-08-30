package com.daqem.knot.registry.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * A unified factory for creating Menu instances without extra data.
 */
@FunctionalInterface
public interface SimpleMenuConstructor<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory inventory);
}