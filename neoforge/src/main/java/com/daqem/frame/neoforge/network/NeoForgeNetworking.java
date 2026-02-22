package com.daqem.frame.neoforge.network;

import com.daqem.frame.FrameMod;
import com.daqem.frame.network.ClientboundContext;
import com.daqem.frame.network.Networking;
import com.daqem.frame.network.ServerboundContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@EventBusSubscriber(modid = FrameMod.MOD_ID)
public class NeoForgeNetworking implements Networking {

    private static final List<Consumer<RegisterPayloadHandlersEvent>> PENDING_REGISTRATIONS = new ArrayList<>();

    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PENDING_REGISTRATIONS.forEach(registration -> registration.accept(event));
        PENDING_REGISTRATIONS.clear();
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, ServerboundContext> handler) {
        PENDING_REGISTRATIONS.add(event -> {
            event.registrar(type.id().getNamespace()).playToServer(type, codec, (payload, context) -> {
                context.enqueueWork(() -> {
                    handler.accept(payload, () -> (ServerPlayer) context.player());
                });
            });
        });
    }

    @Override
    public <T extends CustomPacketPayload> void registerClientbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, BiConsumer<T, ClientboundContext> handler) {
        PENDING_REGISTRATIONS.add(event -> {
            event.registrar(type.id().getNamespace()).playToClient(type, codec, (payload, context) -> {
                context.enqueueWork(() -> {
                    handler.accept(payload, context::player);
                });
            });
        });
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sendToPlayers(Iterable<ServerPlayer> players, CustomPacketPayload payload) {
        for (ServerPlayer player : players) {
            PacketDistributor.sendToPlayer(player, payload);
        }
    }

    @Override
    public boolean canSendToPlayer(ServerPlayer player, CustomPacketPayload.Type<?> type) {
        return player.connection.hasChannel(type.id());
    }

    @Override
    public boolean canSendToServer(CustomPacketPayload.Type<?> type) {
        var connection = net.minecraft.client.Minecraft.getInstance().getConnection();
        return connection != null && connection.hasChannel(type.id());
    }
}