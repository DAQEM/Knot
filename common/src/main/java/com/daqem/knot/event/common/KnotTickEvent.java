package com.daqem.knot.event.common;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.server.MinecraftServer;

public interface KnotTickEvent {

    Event<Server> SERVER_PRE = EventFactory.createLoop(Server.class);
    Event<Server> SERVER_POST = EventFactory.createLoop(Server.class);

    Event<Level> LEVEL_PRE = EventFactory.createLoop(Level.class);
    Event<Level> LEVEL_POST = EventFactory.createLoop(Level.class);

    Event<Player> PLAYER_PRE = EventFactory.createLoop(Player.class);
    Event<Player> PLAYER_POST = EventFactory.createLoop(Player.class);

    interface Server {
        void tick(MinecraftServer server);
    }

    interface Level {
        void tick(net.minecraft.world.level.Level level);
    }

    interface Player {
        void tick(net.minecraft.world.entity.player.Player player);
    }
}
