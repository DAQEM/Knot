package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.KeyMappingRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeyMappingRegistry implements KeyMappingRegistry {

    @Override
    public void register(KeyMapping mapping) {
        KeyBindingHelper.registerKeyBinding(mapping);
    }
}