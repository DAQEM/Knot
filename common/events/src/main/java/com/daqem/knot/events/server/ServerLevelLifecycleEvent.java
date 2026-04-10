package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.server.level.ServerLevel;

public interface ServerLevelLifecycleEvent {

    Event<ServerLevelLoad> SERVER_LEVEL_LOAD = EventFactory.createLoop(ServerLevelLoad.class);
    Event<ServerLevelUnload> SERVER_LEVEL_UNLOAD = EventFactory.createLoop(ServerLevelUnload.class);
    Event<ServerLevelSave> SERVER_LEVEL_SAVE = EventFactory.createLoop(ServerLevelSave.class);

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
}