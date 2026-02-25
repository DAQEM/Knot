package com.daqem.knot.event.lifecycle;

import com.daqem.knot.event.Event;
import com.daqem.knot.event.EventFactory;
import net.minecraft.client.Minecraft;

/**
 * Lifecycle events specific to the physical client environment.
 */
public interface KnotClientLifecycleEvent {

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