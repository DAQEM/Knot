package com.daqem.knot.fabric.events;

import com.daqem.knot.events.EventResult;
import com.daqem.knot.events.common.entity.player.PlayerEvent;
import com.daqem.knot.events.server.*;
import com.daqem.knot.events.common.block.BlockEvent;
import com.daqem.knot.events.common.loot.LootEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import org.apache.commons.lang3.mutable.MutableObject;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import com.daqem.knot.events.client.ClientCommandEvent;

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

        // Player Connections
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerEvent.PLAYER_JOIN.invoker().onPlayerJoin(handler.player);
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            PlayerEvent.PLAYER_QUIT.invoker().onPlayerQuit(handler.player);
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerEvent.PLAYER_RESPAWN.invoker().onPlayerRespawn(newPlayer, alive);
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            PlayerEvent.PLAYER_CLONE.invoker().onPlayerClone(oldPlayer, newPlayer, !alive);
        });

        // Server World State
        ServerLevelEvents.LOAD.register((server, world) ->
                ServerLevelLifecycleEvent.SERVER_LEVEL_LOAD.invoker().onServerLevelLoad(world));
        ServerLevelEvents.UNLOAD.register((server, world) ->
                ServerLevelLifecycleEvent.SERVER_LEVEL_UNLOAD.invoker().onServerLevelUnload(world));
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk, generated) ->
                ServerChunkEvent.LOAD.invoker().onChunkLoad(world, chunk, !generated));
        ServerChunkEvents.CHUNK_UNLOAD.register((world, chunk) ->
                ServerChunkEvent.UNLOAD.invoker().onChunkUnload(world, chunk));

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

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
                ClientCommandEvent.REGISTER.invoker().onRegister(dispatcher);
            });
        }
    }
}