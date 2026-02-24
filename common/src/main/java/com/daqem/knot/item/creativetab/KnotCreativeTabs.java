package com.daqem.knot.item.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The main user-facing API for creating and modifying Creative Mode Tabs.
 */
public final class KnotCreativeTabs {

    private static final KnotCreativeTabsProvider PROVIDER = ServiceLoader.load(KnotCreativeTabsProvider.class)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No KnotCreativeTabsProvider implementation found!"));

    private KnotCreativeTabs() {}

    /**
     * Builds a standard Creative Mode Tab.
     * To be used directly inside your standard {@code KnotRegistry.register(...)} call.
     *
     * @param title The translation component for the tab title.
     * @param icon A supplier returning the ItemStack to display as the icon.
     */
    public static CreativeModeTab build(Component title, Supplier<ItemStack> icon) {
        return PROVIDER.buildTab(title, icon);
    }

    /**
     * Modifies an existing Creative Mode Tab. Perfect for adding your mod's items into vanilla tabs.
     *
     * @param tabKey The ResourceKey of the tab to modify (e.g., CreativeModeTabs.INGREDIENTS).
     * @param populator A consumer providing the TabPopulator to safely add your items.
     */
    public static void modify(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator) {
        PROVIDER.modifyTab(tabKey, populator);
    }
}