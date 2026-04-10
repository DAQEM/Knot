package com.daqem.knot.test.network;

import com.daqem.knot.Knot;

public interface TestNetworking {

    static void init() {
        Knot.NETWORKING.registerClientbound(ClientboundPayload.TYPE, ClientboundPayload.CODEC, () -> ClientboundPayloadHandler::handle);
        Knot.NETWORKING.registerServerbound(ServerboundPayload.TYPE, ServerboundPayload.CODEC, () -> ServerboundPayloadHandler::handle);
    }
}
