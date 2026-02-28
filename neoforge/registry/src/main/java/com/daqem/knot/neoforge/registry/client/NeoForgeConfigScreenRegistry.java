package com.daqem.knot.neoforge.registry.client;

import com.daqem.knot.registry.client.ConfigScreenRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Function;

public class NeoForgeConfigScreenRegistry implements ConfigScreenRegistry {

    @Override
    public void register(String modId, Function<Screen, Screen> screenFactory) {
        ModList.get().getModContainerById(modId).ifPresent(container ->
                container.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) ->
                        screenFactory.apply(parent)));
    }
}