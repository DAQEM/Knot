package com.daqem.knot.fabric.client;

import com.daqem.knot.client.KnotModClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricKnotModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KnotModClient.init();
    }
}
