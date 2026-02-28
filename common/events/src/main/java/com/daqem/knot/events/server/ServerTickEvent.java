package com.daqem.knot.events.server;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.server.dedicated.DedicatedServer;

public interface ServerTickEvent {

    Event<Server> DEDICATED_SERVER_PRE = EventFactory.createLoop(Server.class);
    Event<Server> DEDICATED_SERVER_POST = EventFactory.createLoop(Server.class);

    Event<ServerLevel> SERVER_LEVEL_PRE = EventFactory.createLoop(ServerLevel.class);
    Event<ServerLevel> SERVER_LEVEL_POST = EventFactory.createLoop(ServerLevel.class);

    Event<ServerPlayer> SERVER_PLAYER_PRE = EventFactory.createLoop(ServerPlayer.class);
    Event<ServerPlayer> SERVER_PLAYER_POST = EventFactory.createLoop(ServerPlayer.class);

    interface Server {
        void tick(DedicatedServer server);
    }

    interface ServerLevel {
        void tick(net.minecraft.server.level.ServerLevel serverLevel);
    }

    interface ServerPlayer {
        void tick(net.minecraft.server.level.ServerPlayer serverPlayer);
    }
}
