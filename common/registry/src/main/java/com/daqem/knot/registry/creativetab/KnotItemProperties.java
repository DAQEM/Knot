package com.daqem.knot.registry.creativetab;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public final class KnotItemProperties {

    private KnotItemProperties() {}

    /**
     * Assigns a Creative Mode Tab to an Item.Properties builder.
     *
     * @param properties The properties builder
     * @param tabKey     The resource key of the creative tab
     * @return The same properties builder for fluent chaining
     */
    public static Item.Properties setTab(Item.Properties properties, ResourceKey<CreativeModeTab> tabKey) {
        ((ItemPropertiesExtension) properties).knot$tab(tabKey);
        return properties;
    }
}