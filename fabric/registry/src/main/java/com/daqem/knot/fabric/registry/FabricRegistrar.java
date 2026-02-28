package com.daqem.knot.fabric.registry;

import com.daqem.knot.registry.Registrar;
import com.daqem.knot.registry.Registry;

public class FabricRegistrar implements Registrar {

    @Override
    public <T> Registry<T> createRegistry(net.minecraft.core.Registry<T> registry, String modId) {
        return new FabricRegistry<>(registry, modId);
    }
}