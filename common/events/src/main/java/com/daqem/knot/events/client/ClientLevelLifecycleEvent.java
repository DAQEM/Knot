package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.multiplayer.ClientLevel;

public interface ClientLevelLifecycleEvent {

    Event<ClientLevelLoad> CLIENT_LEVEL_LOAD = EventFactory.createLoop(ClientLevelLoad.class);
    Event<ClientLevelUnload> CLIENT_LEVEL_UNLOAD = EventFactory.createLoop(ClientLevelUnload.class);

    @FunctionalInterface
    interface ClientLevelLoad {
        void onClientLevelLoad(ClientLevel level);
    }

    @FunctionalInterface
    interface ClientLevelUnload {
        void onClientLevelUnload(ClientLevel level);
    }
}