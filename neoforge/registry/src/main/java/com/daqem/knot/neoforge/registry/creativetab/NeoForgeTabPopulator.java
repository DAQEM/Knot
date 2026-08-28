package com.daqem.knot.neoforge.registry.creativetab;

import com.daqem.knot.registry.creativetab.TabPopulator;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class NeoForgeTabPopulator implements TabPopulator {

    private final BuildCreativeModeTabContentsEvent event;

    public NeoForgeTabPopulator(BuildCreativeModeTabContentsEvent event) {
        this.event = event;
    }

    @Override
    public HolderLookup.Provider getLookup() {
        return event.getParameters().holders();
    }

    @Override
    public void add(ItemLike... items) {
        for (ItemLike item : items) {
            event.accept(item);
        }
    }

    @Override
    public void add(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            event.accept(stack);
        }
    }

    @Override
    public void addAfter(ItemLike target, ItemLike... items) {
        for (ItemLike item : items) {
            event.insertAfter(new ItemStack(target), new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @Override
    public void addAfter(ItemStack target, ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            event.insertAfter(target, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @Override
    public void addBefore(ItemLike target, ItemLike... items) {
        for (ItemLike item : items) {
            event.insertBefore(new ItemStack(target), new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @Override
    public void addBefore(ItemStack target, ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            event.insertBefore(target, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}