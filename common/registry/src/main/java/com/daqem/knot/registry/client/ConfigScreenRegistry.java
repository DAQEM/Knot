package com.daqem.knot.registry.client;

import net.minecraft.client.gui.screens.Screen;
import java.util.function.Function;

public interface ConfigScreenRegistry {
    /**
     * Registers a configuration screen for your mod.
     * @param modId Your Mod ID
     * @param screenFactory A function that takes the parent screen and returns the new config screen
     */
    void register(String modId, Function<Screen, Screen> screenFactory);
}