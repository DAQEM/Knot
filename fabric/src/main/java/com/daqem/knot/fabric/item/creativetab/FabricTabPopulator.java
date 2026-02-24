package com.daqem.knot.fabric.item.creativetab;

import com.daqem.knot.item.creativetab.TabPopulator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

public class FabricTabPopulator implements TabPopulator {

    private final FabricItemGroupEntries entries;

    public FabricTabPopulator(FabricItemGroupEntries entries) {
        this.entries = entries;
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
        entries.addAfter(target, items);
    }

    @Override
    public void addAfter(ItemStack target, ItemStack... stacks) {
        entries.addAfter(target, List.of(stacks));
    }

    @Override
    public void addBefore(ItemLike target, ItemLike... items) {
        entries.addBefore(target, items);
    }

    @Override
    public void addBefore(ItemStack target, ItemStack... stacks) {
        entries.addBefore(target, List.of(stacks));
    }
}