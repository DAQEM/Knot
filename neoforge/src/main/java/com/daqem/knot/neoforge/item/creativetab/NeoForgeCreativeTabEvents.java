package com.daqem.knot.neoforge.item.creativetab;

import com.daqem.knot.KnotMod;
import com.daqem.knot.item.creativetab.TabPopulator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgeCreativeTabEvents {

    @SubscribeEvent
    public static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        List<Consumer<TabPopulator>> modifiers = NeoForgeKnotCreativeTabsProvider.MODIFIERS.get(event.getTabKey());

        if (modifiers != null && !modifiers.isEmpty()) {
            NeoForgeTabPopulator wrapper = new NeoForgeTabPopulator(event);
            for (Consumer<TabPopulator> populator : modifiers) {
                populator.accept(wrapper);
            }
        }
    }
}