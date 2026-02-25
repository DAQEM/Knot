package com.daqem.knot.fabric.event;

import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.daqem.knot.event.lifecycle.KnotServerLifecycleEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public final class FabricLifecycleHooks {

    public static void register() {
        // Server State
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            KnotServerLifecycleEvent.BEFORE_START.invoker().onServerBeforeStart(server);
            KnotServerLifecycleEvent.STARTING.invoker().onServerStarting(server);
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server ->
                KnotServerLifecycleEvent.STARTED.invoker().onServerStarted(server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server ->
                KnotServerLifecycleEvent.STOPPING.invoker().onServerStopping(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server ->
                KnotServerLifecycleEvent.STOPPED.invoker().onServerStopped(server));

        // Server World State
        ServerWorldEvents.LOAD.register((server, world) ->
                KnotLevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(world));
        ServerWorldEvents.UNLOAD.register((server, world) ->
                KnotLevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(world));
    }
}