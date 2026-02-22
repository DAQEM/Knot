package com.daqem.frame.test.network;

import com.daqem.frame.network.ClientboundContext;
import com.daqem.frame.test.Test;
import org.jetbrains.annotations.NotNull;

public final class ClientboundPayloadHandler {

    public static void handle(@NotNull ClientboundPayload clientboundPayload, ClientboundContext clientboundContext) {
        Test.API.info("Received clientbound payload with data: {}", clientboundPayload.data());
    }

    private ClientboundPayloadHandler() {}
}
