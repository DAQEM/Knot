package com.daqem.knot.test.network;

import com.daqem.knot.network.ServerboundContext;
import com.daqem.knot.test.Test;
import org.jetbrains.annotations.NotNull;

public final class ServerboundPayloadHandler {

    public static void handle(@NotNull ServerboundPayload serverboundPayload, ServerboundContext serverboundContext) {
        Test.API.info("Received serverbound payload with data: {}", serverboundPayload.data());
    }

    private ServerboundPayloadHandler() {}
}
