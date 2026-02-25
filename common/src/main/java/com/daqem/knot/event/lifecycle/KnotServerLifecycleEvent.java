package com.daqem.knot.event.lifecycle;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.server.MinecraftServer;

/**
 * Lifecycle events dictating the state of a Minecraft Server (Dedicated or Integrated).
 */
public interface KnotServerLifecycleEvent {

    Event<ServerBeforeStart> BEFORE_START = EventFactory.createLoop(ServerBeforeStart.class);
    Event<ServerStarting> STARTING = EventFactory.createLoop(ServerStarting.class);
    Event<ServerStarted> STARTED = EventFactory.createLoop(ServerStarted.class);
    Event<ServerStopping> STOPPING = EventFactory.createLoop(ServerStopping.class);
    Event<ServerStopped> STOPPED = EventFactory.createLoop(ServerStopped.class);

    @FunctionalInterface
    interface ServerBeforeStart {
        /**
         * Fired right before the server begins its initialization logic.
         */
        void onServerBeforeStart(MinecraftServer server);
    }

    @FunctionalInterface
    interface ServerStarting {
        /**
         * Fired while the server is starting. This is the optimal time to register commands.
         */
        void onServerStarting(MinecraftServer server);
    }

    @FunctionalInterface
    interface ServerStarted {
        /**
         * Fired when the server has successfully started and is ready to accept players.
         */
        void onServerStarted(MinecraftServer server);
    }

    @FunctionalInterface
    interface ServerStopping {
        /**
         * Fired when the server initiates its shutdown sequence.
         */
        void onServerStopping(MinecraftServer server);
    }

    @FunctionalInterface
    interface ServerStopped {
        /**
         * Fired when the server has fully and completely shut down.
         */
        void onServerStopped(MinecraftServer server);
    }
}