package com.daqem.knot.fabric;

import com.daqem.knot.KnotMod;
import com.daqem.knot.fabric.events.FabricEventHooks;
import net.fabricmc.api.ModInitializer;

public class FabricKnotMod implements ModInitializer {

    @Override
    public void onInitialize() {
        KnotMod.init();
        FabricEventHooks.register();
    }
}
