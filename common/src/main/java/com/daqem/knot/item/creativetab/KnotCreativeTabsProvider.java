package com.daqem.knot.item.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The internal service interface loaded by the ServiceLoader per platform.
 */
public interface KnotCreativeTabsProvider {

    CreativeModeTab buildTab(Component title, Supplier<ItemStack> icon);

    void modifyTab(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator);
}