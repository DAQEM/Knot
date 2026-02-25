package com.daqem.knot.neoforge.event;

import com.daqem.knot.KnotMod;
import com.daqem.knot.event.EventResult;
import com.daqem.knot.event.KnotChatEvent;
import com.daqem.knot.event.lifecycle.KnotLevelLifecycleEvent;
import com.daqem.knot.event.lifecycle.KnotServerLifecycleEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.*;
import org.apache.commons.lang3.mutable.MutableObject;

@EventBusSubscriber(modid = KnotMod.MOD_ID)
public class NeoForgeLifecycleHooks {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        KnotServerLifecycleEvent.BEFORE_START.invoker().onServerBeforeStart(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        KnotServerLifecycleEvent.STARTING.invoker().onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        KnotServerLifecycleEvent.STARTED.invoker().onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        KnotServerLifecycleEvent.STOPPING.invoker().onServerStopping(event.getServer());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        KnotServerLifecycleEvent.STOPPED.invoker().onServerStopped(event.getServer());
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            KnotLevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            KnotLevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        MutableObject<Component> mutable = new MutableObject<>(event.getMessage());
        KnotChatEvent.DECORATE.invoker().onDecorateChat(event.getPlayer(), mutable);
        event.setMessage(mutable.get());

        EventResult result = KnotChatEvent.RECEIVED.invoker().onReceiveChat(event.getPlayer(), event.getMessage());
        if (result.cancelsEvent()) {
            event.setCanceled(true);
        }
    }
}