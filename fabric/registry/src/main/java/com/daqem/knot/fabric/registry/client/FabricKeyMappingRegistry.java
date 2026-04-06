package com.daqem.knot.fabric.registry.client;

import com.daqem.knot.registry.client.KeyMappingRegistry;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class FabricKeyMappingRegistry implements KeyMappingRegistry {

    @Override
    public void register(KeyMapping mapping) {
        KeyMappingHelper.registerKeyMapping(mapping);
    }
}