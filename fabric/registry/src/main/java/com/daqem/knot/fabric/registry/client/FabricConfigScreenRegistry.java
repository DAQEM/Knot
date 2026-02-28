package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.ConfigScreenRegistry;
import net.minecraft.client.gui.screens.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FabricConfigScreenRegistry implements ConfigScreenRegistry {

    public static final Map<String, Function<Screen, Screen>> FACTORIES = new HashMap<>();

    @Override
    public void register(String modId, Function<Screen, Screen> screenFactory) {
        FACTORIES.put(modId, screenFactory);
    }
}