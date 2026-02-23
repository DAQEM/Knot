package com.daqem.knot.fabric.network;

import com.daqem.knot.network.ClientboundContext;
import com.daqem.knot.network.Networking;
import com.daqem.knot.network.ServerboundContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class FabricNetworking implements Networking {

    @Override
    public <T extends CustomPacketPayload> void registerServerbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, ServerboundContext> handler) {
        PayloadTypeRegistry.playC2S().register(type, codec);
        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
            context.server().execute(() -> {
                handler.accept(payload, context::player);
            });
        });
    }

    @Override
    public <T extends CustomPacketPayload> void registerClientbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, ClientboundContext> handler) {
        PayloadTypeRegistry.playS2C().register(type, codec);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientHandlerIsolator.register(type, handler);
        }
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientHandlerIsolator.send(payload);
        }
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload payload) {
        for (ServerPlayer player : players) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    @Override
    public boolean canSendToPlayer(ServerPlayer player, CustomPacketPayload.Type<?> type) {
        return ServerPlayNetworking.canSend(player, type.id());
    }

    @Override
    public boolean canSendToServer(CustomPacketPayload.Type<?> type) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            return ClientHandlerIsolator.canSendToServer(type);
        }
        return false;
    }

    private static class ClientHandlerIsolator {
        static <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<@NotNull T> type, BiConsumer<T, ClientboundContext> handler) {
            ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
                context.client().execute(() -> {
                    handler.accept(payload, context::player);
                });
            });
        }

        static void send(CustomPacketPayload payload) {
            ClientPlayNetworking.send(payload);
        }

        static boolean canSendToServer(CustomPacketPayload.Type<?> type) {
            return ClientPlayNetworking.canSend(type.id());
        }
    }
}