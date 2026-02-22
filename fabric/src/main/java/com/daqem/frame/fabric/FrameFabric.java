package com.daqem.frame.fabric;

import com.daqem.frame.FrameMod;
import net.fabricmc.api.ModInitializer;

public class FrameFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FrameMod.init();
    }
}
