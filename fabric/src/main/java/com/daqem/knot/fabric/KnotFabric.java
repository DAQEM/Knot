package com.daqem.knot.fabric;

import com.daqem.knot.KnotMod;
import com.daqem.knot.fabric.event.FabricLifecycleHooks;
import net.fabricmc.api.ModInitializer;

public class KnotFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        KnotMod.init();
        FabricLifecycleHooks.register();
    }
}
