package com.daqem.frame.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * The core networking service interface for Frame.
 * Loaded internally via ServiceLoader.
 */
public interface Networking {

    <T extends CustomPacketPayload> void registerServerbound(
            CustomPacketPayload.Type<@NotNull T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            BiConsumer<T, ServerboundContext> handler
    );

    <T extends CustomPacketPayload> void registerClientbound(
            CustomPacketPayload.Type<@NotNull T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            BiConsumer<T, ClientboundContext> handler
    );

    void sendToServer(CustomPacketPayload payload);

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload payload);

    /**
     * Checks if the given player has the mod installed and can receive this packet.
     */
    boolean canSendToPlayer(ServerPlayer player, CustomPacketPayload.Type<?> type);

    /**
     * Checks if the server we are currently connected to can receive this packet.
     */
    boolean canSendToServer(CustomPacketPayload.Type<?> type);
}