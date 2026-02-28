package com.daqem.knot.registry.creativetab;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

/**
 * An extension interface injected into {@link Item.Properties} to allow assigning items
 * directly to a Creative Mode Tab during initialization.
 */
public interface ItemPropertiesExtension {

    /**
     * Appends this item to the end of the specified Creative Mode Tab.
     *
     * @param tabKey The ResourceKey of the tab (e.g. VanillaTabs.COMBAT or your custom tab key)
     * @return The properties instance for chaining.
     */
    default Item.Properties knot$tab(ResourceKey<CreativeModeTab> tabKey) {
        throw new UnsupportedOperationException("Implemented via Mixin");
    }

    /**
     * Internal method used to retrieve the assigned tab key during Item construction.
     */
    default ResourceKey<CreativeModeTab> knot$getTabKey() {
        throw new UnsupportedOperationException("Implemented via Mixin");
    }
}