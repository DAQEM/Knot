package com.daqem.knot.item.creativetab;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * A simplified, intuitive interface for adding items to a Creative Mode Tab.
 */
public interface TabPopulator {

    /**
     * Appends the given items to the end of the tab.
     */
    void add(ItemLike... items);

    /**
     * Appends the given ItemStacks to the end of the tab.
     */
    void add(ItemStack... stacks);

    /**
     * Inserts items immediately after a specific target item.
     */
    void addAfter(ItemLike target, ItemLike... items);

    /**
     * Inserts ItemStacks immediately after a specific target ItemStack.
     */
    void addAfter(ItemStack target, ItemStack... stacks);

    /**
     * Inserts items immediately before a specific target item.
     */
    void addBefore(ItemLike target, ItemLike... items);

    /**
     * Inserts ItemStacks immediately before a specific target ItemStack.
     */
    void addBefore(ItemStack target, ItemStack... stacks);
}