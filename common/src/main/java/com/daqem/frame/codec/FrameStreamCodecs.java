package com.daqem.frame.codec;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public final class FrameStreamCodecs {
    public static <B extends FriendlyByteBuf, V extends Enum<V>> StreamCodec<B, V> enumCodec(Class<V> enumClass) {
        return new StreamCodec<>() {
            @Override
            public @NotNull V decode(@NotNull B buf) {
                return buf.readEnum(enumClass);
            }

            @Override
            public void encode(@NotNull B buf, @NotNull V value) {
                buf.writeEnum(value);
            }
        };
    }
}
