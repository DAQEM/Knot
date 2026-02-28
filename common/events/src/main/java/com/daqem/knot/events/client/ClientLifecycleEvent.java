package com.daqem.knot.events.client;

import com.daqem.knot.events.Event;
import com.daqem.knot.events.EventFactory;
import net.minecraft.client.Minecraft;

/**
 * Lifecycle events specific to the physical client environment.
 */
public interface ClientLifecycleEvent {

    Event<ClientStarted> STARTED = EventFactory.createLoop(ClientStarted.class);
    Event<ClientStopping> STOPPING = EventFactory.createLoop(ClientStopping.class);

    @FunctionalInterface
    interface ClientStarted {
        /**
         * Fired exactly when the Minecraft client has finished initializing and the game loop starts.
         */
        void onClientStarted(Minecraft client);
    }

    @FunctionalInterface
    interface ClientStopping {
        /**
         * Fired when the Minecraft client begins its shutdown sequence.
         */
        void onClientStopping(Minecraft client);
    }
}