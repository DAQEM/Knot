package com.daqem.knot.fabric.registry;

import com.daqem.knot.registry.KnotRegistrar;
import com.daqem.knot.registry.KnotRegistry;
import net.minecraft.core.Registry;

public class FabricKnotRegistrar implements KnotRegistrar {

    @Override
    public <T> KnotRegistry<T> createRegistry(Registry<T> registry, String modId) {
        return new FabricKnotRegistry<>(registry, modId);
    }
}