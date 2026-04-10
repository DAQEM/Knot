package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.multiplayer.ClientLevel;

public interface ClientLevelLifecycleEvent {

    Event<ClientLevelLifecycleEvent.ClientLevelLoad> CLIENT_LEVEL_LOAD = EventFactory.createLoop(ClientLevelLifecycleEvent.ClientLevelLoad.class);
    Event<ClientLevelLifecycleEvent.ClientLevelUnload> CLIENT_LEVEL_UNLOAD = EventFactory.createLoop(ClientLevelLifecycleEvent.ClientLevelUnload.class);

    @FunctionalInterface
    interface ClientLevelLoad {
        void onClientLevelLoad(ClientLevel level);
    }

    @FunctionalInterface
    interface ClientLevelUnload {
        void onClientLevelUnload(ClientLevel level);
    }
}
