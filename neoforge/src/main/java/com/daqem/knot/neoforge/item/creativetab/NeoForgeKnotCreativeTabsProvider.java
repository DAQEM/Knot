package com.daqem.knot.neoforge.item.creativetab;

import com.daqem.knot.item.creativetab.KnotCreativeTabsProvider;
import com.daqem.knot.item.creativetab.TabPopulator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeKnotCreativeTabsProvider implements KnotCreativeTabsProvider {

    // Package-private queue mapped safely for our event listener to consume later
    static final Map<ResourceKey<CreativeModeTab>, List<Consumer<TabPopulator>>> MODIFIERS = new HashMap<>();

    @Override
    public CreativeModeTab buildTab(Component title, Supplier<ItemStack> icon) {
        return CreativeModeTab.builder()
                .title(title)
                .icon(icon)
                .build();
    }

    @Override
    public void modifyTab(ResourceKey<CreativeModeTab> tabKey, Consumer<TabPopulator> populator) {
        MODIFIERS.computeIfAbsent(tabKey, k -> new ArrayList<>()).add(populator);
    }
}