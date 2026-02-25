package com.daqem.knot.fabric.event;

import com.daqem.knot.event.EventResult;
import com.daqem.knot.event.KnotChatEvent;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.daqem.knot.event.lifecycle.KnotServerLifecycleEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableObject;

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

        // Chat Decoration and Reception
        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE, (sender, message) -> {
            MutableObject<Component> mutable = new MutableObject<>(message);
            KnotChatEvent.DECORATE.invoker().onDecorateChat(sender, mutable);
            return mutable.get();
        });

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
            EventResult result = KnotChatEvent.RECEIVED.invoker().onReceiveChat(sender, message.decoratedContent());
            return !result.cancelsEvent();
        });
    }
}