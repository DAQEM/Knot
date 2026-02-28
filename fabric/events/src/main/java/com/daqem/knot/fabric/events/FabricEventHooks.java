package com.daqem.knot.fabric.events;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.LevelLifecycleEvent;
import com.daqem.knot.events.common.block.BlockEvent;
import com.daqem.knot.events.common.loot.LootEvent;
import com.daqem.knot.events.server.ServerChatEvent;
import com.daqem.knot.events.server.ServerCommandEvent;
import com.daqem.knot.events.server.ServerLifecycleEvent;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import org.apache.commons.lang3.mutable.MutableObject;

public final class FabricEventHooks {

    public static void register() {
        // Server State
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ServerLifecycleEvent.BEFORE_START.invoker().onServerBeforeStart(server);
            ServerLifecycleEvent.STARTING.invoker().onServerStarting(server);
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server ->
                ServerLifecycleEvent.STARTED.invoker().onServerStarted(server));
        ServerLifecycleEvents.SERVER_STOPPING.register(server ->
                ServerLifecycleEvent.STOPPING.invoker().onServerStopping(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server ->
                ServerLifecycleEvent.STOPPED.invoker().onServerStopped(server));

        // Server World State
        ServerWorldEvents.LOAD.register((server, world) ->
                LevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(world));
        ServerWorldEvents.UNLOAD.register((server, world) ->
                LevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(world));

        // Chat Decoration and Reception
        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE, (sender, message) -> {
            MutableObject<Component> mutable = new MutableObject<>(message);
            ServerChatEvent.DECORATE.invoker().onDecorateChat(sender, mutable);
            return mutable.get();
        });

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
            EventResult result = ServerChatEvent.RECEIVED.invoker().onReceiveChat(sender, message.decoratedContent());
            return !result.cancelsEvent();
        });

        // Loot Table Modification
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            LootEvent.MODIFY_LOOT_TABLE.invoker().onModifyLootTable(
                    key,
                    tableBuilder::withPool,
                    source.isBuiltin()
            );
        });

        // Command Registration
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ServerCommandEvent.REGISTER.invoker().onRegister(dispatcher, registryAccess, environment);
        });

        // Block Clicks
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            EventResult result = BlockEvent.LEFT_CLICK_BLOCK.invoker().onLeftClickBlock(player, hand, pos, direction);
            return result.cancelsEvent() ? InteractionResult.FAIL : InteractionResult.PASS;
        });
    }
}