package com.daqem.knot.fabric.registry.creativetab;

import com.daqem.knot.registry.creativetab.CreativeTabsRegistry;
import com.daqem.knot.registry.creativetab.TabPopulator;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FabricCreativeTabsRegistry implements CreativeTabsRegistry {

    @Override
    public CreativeModeTab build(Component title, Supplier<ItemStack> icon) {
        return FabricCreativeModeTab.builder()
                .title(title)
                .icon(icon)
                .build();
    }

    @Override
    public void modify(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator) {
        CreativeModeTabEvents.modifyOutputEvent(tabKey).register(entries -> {
            populator.accept(new FabricTabPopulator(entries));
        });
    }
}