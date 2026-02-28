package com.daqem.knot.events.common;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;

/**
 * Lifecycle events related to worlds/levels being loaded, unloaded, or saved.
 */
public interface LevelLifecycleEvent {

    Event<ServerLevelLoad> SERVER_LEVEL_LOAD = EventFactory.createLoop(ServerLevelLoad.class);
    Event<ServerLevelUnload> SERVER_LEVEL_UNLOAD = EventFactory.createLoop(ServerLevelUnload.class);
    Event<ServerLevelSave> SERVER_LEVEL_SAVE = EventFactory.createLoop(ServerLevelSave.class);

    Event<ClientLevelLoad> CLIENT_LEVEL_LOAD = EventFactory.createLoop(ClientLevelLoad.class);
    Event<ClientLevelUnload> CLIENT_LEVEL_UNLOAD = EventFactory.createLoop(ClientLevelUnload.class);

    @FunctionalInterface
    interface ServerLevelLoad {
        void onServerLevelLoad(ServerLevel level);
    }

    @FunctionalInterface
    interface ServerLevelUnload {
        void onServerLevelUnload(ServerLevel level);
    }

    @FunctionalInterface
    interface ServerLevelSave {
        void onServerLevelSave(ServerLevel level);
    }

    @FunctionalInterface
    interface ClientLevelLoad {
        void onClientLevelLoad(ClientLevel level);
    }

    @FunctionalInterface
    interface ClientLevelUnload {
        void onClientLevelUnload(ClientLevel level);
    }
}