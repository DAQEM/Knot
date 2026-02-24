package com.daqem.knot.fabric.item.creativetab;

import com.daqem.knot.item.creativetab.KnotCreativeTabsProvider;
import com.daqem.knot.item.creativetab.TabPopulator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FabricKnotCreativeTabsProvider implements KnotCreativeTabsProvider {

    @Override
    public CreativeModeTab buildTab(Component title, Supplier<ItemStack> icon) {
        return FabricItemGroup.builder()
                .title(title)
                .icon(icon)
                .build();
    }

    @Override
    public void modifyTab(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator) {
        ItemGroupEvents.modifyEntriesEvent(tabKey).register(entries -> {
            populator.accept(new FabricTabPopulator(entries));
        });
    }
}