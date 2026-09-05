package com.daqem.knot.fabric.registry.creativetab;

import com.daqem.knot.registry.creativetab.TabPopulator;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public class FabricTabPopulator implements TabPopulator {

    private final FabricCreativeModeTabOutput entries;

    public FabricTabPopulator(FabricCreativeModeTabOutput entries) {
        this.entries = entries;
    }

    @Override
    public HolderLookup.Provider getLookup() {
        return entries.getContext().holders();
    }

    @Override
    public void add(ItemLike... items) {
        for (ItemLike item : items) {
            entries.accept(item);
        }
    }

    @Override
    public void add(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            entries.accept(stack);
        }
    }

    @Override
    public void addAfter(ItemLike target, ItemLike... items) {
        entries.insertAfter(target, items);
    }

    @Override
    public void addAfter(ItemStack target, ItemStack... stacks) {
        entries.insertAfter(target, List.of(stacks));
    }

    @Override
    public void addBefore(ItemLike target, ItemLike... items) {
        entries.insertBefore(target, items);
    }

    @Override
    public void addBefore(ItemStack target, ItemStack... stacks) {
        entries.insertBefore(target, List.of(stacks));
    }
}