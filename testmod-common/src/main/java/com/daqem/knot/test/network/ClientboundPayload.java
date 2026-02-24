package com.daqem.knot.test.network;

import com.daqem.knot.test.Test;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundPayload(String data) implements CustomPacketPayload {

    // 1. The unique identifier for this packet
    public static final Type<@NotNull ClientboundPayload> TYPE = new Type<>(Test.API.getId("clientbound_payload"));

    // 2. The Codec that tells Minecraft how to write and read this record over the network.
    // StreamCodec.composite automatically handles the encoding/decoding in the exact order you provide.
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ClientboundPayload::data,
            ClientboundPayload::new
    );

    // 3. This method is required by the CustomPacketPayload interface
    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
