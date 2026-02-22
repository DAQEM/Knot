package com.daqem.frame.test.network;

import com.daqem.frame.Frame;

public interface TestNetworking {

    static void init() {
        Frame.NETWORKING.registerClientbound(ClientboundPayload.TYPE, ClientboundPayload.CODEC, ClientboundPayloadHandler::handle);
        Frame.NETWORKING.registerServerbound(ServerboundPayload.TYPE, ServerboundPayload.CODEC, ServerboundPayloadHandler::handle);
    }
}
