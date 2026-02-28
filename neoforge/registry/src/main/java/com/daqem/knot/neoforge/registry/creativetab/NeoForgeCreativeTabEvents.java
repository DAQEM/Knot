package com.daqem.knot.neoforge.registry.creativetab;

import com.daqem.knot.api.Constants;
import com.daqem.knot.registry.creativetab.TabPopulator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;
import java.util.function.Consumer;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeCreativeTabEvents {

    @SubscribeEvent
    public static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        List<Consumer<TabPopulator>> modifiers = NeoForgeCreativeTabsRegistry.MODIFIERS.get(event.getTabKey());

        if (modifiers != null && !modifiers.isEmpty()) {
            NeoForgeTabPopulator wrapper = new NeoForgeTabPopulator(event);
            for (Consumer<TabPopulator> populator : modifiers) {
                populator.accept(wrapper);
            }
        }
    }
}