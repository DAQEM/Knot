package com.daqem.knot.test.network;

import com.daqem.knot.networking.ClientboundContext;
import org.jetbrains.annotations.NotNull;

public final class ClientboundPayloadHandler {

    public static void handle(@NotNull ClientboundPayload clientboundPayload, ClientboundContext clientboundContext) {
//        Test.API.info("Received clientbound payload with data: {}", clientboundPayload.data());
    }

    private ClientboundPayloadHandler() {}
}
