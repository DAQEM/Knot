package com.daqem.knot.neoforge.networking;

import com.daqem.knot.api.Constants;
import com.daqem.knot.networking.ClientboundContext;
import com.daqem.knot.networking.NetworkingService;
import com.daqem.knot.networking.ServerboundContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeNetworkingService implements NetworkingService {

    private static final List<Consumer<RegisterPayloadHandlersEvent>> PENDING_REGISTRATIONS = new ArrayList<>();

    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PENDING_REGISTRATIONS.forEach(registration -> registration.accept(event));
        PENDING_REGISTRATIONS.clear();
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, Supplier<BiConsumer<T, ServerboundContext>> handler) {
        PENDING_REGISTRATIONS.add(event ->
                event.registrar(type.id().getNamespace()).playToServer(type, codec, (payload, context) ->
                        context.enqueueWork(() -> handler.get().accept(payload, () -> (ServerPlayer) context.player()))));
    }

    @Override
    public <T extends CustomPacketPayload> void registerClientbound(CustomPacketPayload.Type<@NotNull T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, Supplier<BiConsumer<T, ClientboundContext>> handler) {
        PENDING_REGISTRATIONS.add(event ->
                event.registrar(type.id().getNamespace()).playToClient(type, codec, (payload, context) ->
                        context.enqueueWork(() -> ClientHandlerIsolator.handle(handler.get(), payload, context))));
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            ClientHandlerIsolator.sendToServer(payload);
        }
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
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            return ClientHandlerIsolator.canSendToServer(type);
        }
        return false;
    }

    private static class ClientHandlerIsolator {
        static <T extends CustomPacketPayload> void handle(BiConsumer<T, ClientboundContext> handler, T payload, IPayloadContext context) {
            handler.accept(payload, () -> (net.minecraft.client.player.LocalPlayer) context.player());
        }

        static void sendToServer(CustomPacketPayload payload) {
            net.neoforged.neoforge.client.network.ClientPacketDistributor.sendToServer(payload);
        }

        static boolean canSendToServer(CustomPacketPayload.Type<?> type) {
            var connection = net.minecraft.client.Minecraft.getInstance().getConnection();
            return connection != null && connection.hasChannel(type.id());
        }
    }
}